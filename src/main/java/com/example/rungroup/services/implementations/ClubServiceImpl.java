package com.example.rungroup.services.implementations;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.rungroup.dto.*;
import com.example.rungroup.repos.*;
import com.example.rungroup.security.SecurityUtil;
import com.example.rungroup.services.ClubService;
import com.example.rungroup.services.EventService;
import com.example.rungroup.services.UserService;
import com.example.rungroup.entities.*;
import com.example.rungroup.mappers.ClubMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;


import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClubServiceImpl implements ClubService {

    private ClubRepo clubRepo;
    // private UserRepo userRepo;
    private UserService userSrv;
    // private EventService eventSrv;

    @Override
    public List<ClubDto> findAll() {
        return clubRepo.findAll()
            .stream()
            .map(club -> ClubMapper.mapToClubDto(club))
            .collect(Collectors.toList());
    }

    @Override
    public void save(ClubDto clubDto) {
        String userName = SecurityUtil.getSessionUser();
        ClubEntity club = ClubMapper.mapToClub(clubDto);
        club.setCreatedOn(LocalDateTime.now());
        club.setCreatedBy(userSrv.findByUserName(userName));
        clubRepo.save(club);
    }

    public ClubEntity findEntityById(Long id){
        return clubRepo.findById(id).orElseThrow();
    }

    public ClubDto findById(Long id){  
        return ClubMapper.mapToClubDto(clubRepo.findById(id).orElseThrow());
    }

    public boolean currentUserIsAuthor (Long clubDtoId){
        return findById(clubDtoId).getAuthorId() == userSrv.getCurrentUserEntity().getId(); 
    }

    public void updateClub(ClubDto clubDto, Long clubId){
        //the clubDto comes in without an eventlist or a user attached
        if (userHasAuthority(clubId)){
            clubDto.setUpdatedOn(LocalDateTime.now());
            clubRepo.save(convertToEntity(clubDto, clubId));
        }
        throw new RuntimeException("User does not have authority to modify club with id = "+clubId);
    }

    @Override
    public List<ClubDto> searchClubs(String query){
        return clubRepo.searchClub(query)
            .stream()
            .map(club -> ClubMapper.mapToClubDto(club))
            .collect(Collectors.toList());
    }

    @Override
    public void deleteByid(Long id) {
        if (userHasAuthority(id)){
            if (clubRepo.findById(id).isPresent()){
                clubRepo.deleteById(id);
            } else{
                throw new RuntimeException("no record with id = "+id+" found");
            }
    } else {
        throw new RuntimeException("user doesn't have the authority to delete event");
    }

    }
    @Override
    public boolean userHasAuthority (Long clubDtoId){
        Long currentUserId = userSrv.getCurrentUserEntity().getId();

        return 
        currentUserId != null
        &&
        (findById(clubDtoId).getAuthorId() == currentUserId || userSrv.userIsAdmin());
    }

    private ClubEntity convertToEntity (ClubDto clubDto, Long clubId){
        ClubEntity clubEnt = ClubMapper.mapToClub(clubDto);

        ClubEntity localClubEntity = findEntityById(clubId);
        clubEnt.setId(localClubEntity.getId());
        clubEnt.setEventList(localClubEntity.getEventList());
        clubEnt.setCreatedBy(userSrv.getCurrentUserEntity());
        return clubEnt;
    }

}
