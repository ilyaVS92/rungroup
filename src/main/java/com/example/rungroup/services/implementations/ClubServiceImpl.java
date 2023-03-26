package com.example.rungroup.services.implementations;
import com.example.rungroup.mappers.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.rungroup.dto.*;
import com.example.rungroup.repos.*;
import com.example.rungroup.security.SecurityUtil;
import com.example.rungroup.services.ClubService;
import com.example.rungroup.services.UserService;
import com.example.rungroup.entities.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;


import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClubServiceImpl implements ClubService {

    private ClubRepo clubRepo;
    private UserRepo userRepo;
    private UserService userServ;

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
        club.setCreatedBy(userServ.findByUserName(userName));
        clubRepo.save(club);
    }

    public ClubEntity findEntityById(Long id){
        return clubRepo.findById(id).orElseThrow();
    }

    public ClubDto findById(Long id){  
        return ClubMapper.mapToClubDto(clubRepo.findById(id).orElseThrow());
    }

    public void updateClub(ClubDto clubDto){
        String userName = SecurityUtil.getSessionUser();
        ClubEntity club = ClubMapper.mapToClub(clubDto);
        club.setCreatedBy(userServ.findByUserName(userName));
        clubRepo.save(club);
        // clubRepo.save(ClubMapper.mapToClub(clubDto).setCreatedBy(userServ.findByUserName(SecurityUtil.getSessionUser())));
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
        if (clubRepo.findById(id).isPresent()){
            clubRepo.deleteById(id);
        } else{
            throw new RuntimeException("no record with id = "+id+" found");
        }
    }
}
