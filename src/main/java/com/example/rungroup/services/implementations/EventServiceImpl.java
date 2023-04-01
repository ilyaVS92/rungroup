package com.example.rungroup.services.implementations;

import org.springframework.stereotype.Service;
import com.example.rungroup.entities.*;
import com.example.rungroup.mappers.EventMapper;
import com.example.rungroup.dto.EventDto;
import com.example.rungroup.repos.ClubRepo;
import com.example.rungroup.repos.EventRepo;
import com.example.rungroup.security.SecurityUtil;
import com.example.rungroup.services.ClubService;
import com.example.rungroup.services.EventService;
import com.example.rungroup.services.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService{
    
    // private ClubRepo clubRepo;
    private EventRepo eventRepo;
    private ClubService clubSrv;
    private UserService userSrv;

    @Override
    public List<EventEntity> findAllByClubId(Long id){
        return eventRepo.findAllByClubId(id);
    }

    @Override
    public List<EventDto> findAllEvents() {
        List<EventEntity> events = eventRepo.findAll();
        return events.stream()
            .map(event -> EventMapper.mapToEventDto(event))
            .collect(Collectors.toList());
    }

    @Override
    public EventDto findById(Long id) {
        if (id == null || id == 0) {
            return new EventDto();
        }
        System.out.println("---------------> EventServiceImpl.findById("+id+")");
        EventEntity event = eventRepo.findById(id).get();
        return EventMapper.mapToEventDto(event);
    }

    @Override
    public boolean save(EventDto eventDto, Long clubId) {
            EventEntity event = convertToEventEntity(eventDto, clubId);
            eventRepo.save(event);
            System.out.println("Status OK: save successful");
            return true;
        // }
        // return false;
    }


    public boolean updateEvent(EventDto event, Long eventId, Long clubId){
        System.out.println("---------------> EventSrvImpl.updatedEvent ~~~~~~~> usr= "+userSrv.getCurrentUserEntity().toString());
        // System.out.println("---------------> EventSrvImpl.updatedEvent ~~~~~~~> dto author id= "+event.getCreatedBy().toString());
        System.out.println("---------------> EventSrvImpl.updatedEvent ~~~~~~~> event DTO = "+event.toString());    
        // System.out.println("---------------> EventSrvImpl.updatedEvent ~~~~~~~> event ENT= "+localEventEntity.toString());

        EventEntity updatedEvent = convertToEventEntity(event, clubId);
        updatedEvent.setId(eventId);
        eventRepo.save(updatedEvent);
        return true;
    }

    private EventEntity convertToEventEntity(EventDto event, Long clubId){
        ClubEntity localClubEntity = clubSrv.findEntityById(clubId);
        EventEntity localEventEntity = EventMapper.mapToEvent(event);
        localEventEntity.setClub(localClubEntity);
        localEventEntity.setCreatedBy(userSrv.getCurrentUserEntity());
        return localEventEntity;
    }

    public boolean userHasAuthority (Long eventDtoId){
        System.out.println("---------------> checking if USER has AUTH for EVENT "+eventDtoId);
        Long currentUserId = userSrv.getCurrentUserEntity().getId();
        return 
            currentUserId!=null
            &&
            (findById(eventDtoId).getId() == userSrv.getCurrentUserEntity().getId()
         || clubSrv.userHasAuthority(findById(eventDtoId).getClub().getId()) 
         || userSrv.userIsAdmin());
        // return false;
    }

    @Override
    public boolean delete(Long eventId) {
        System.out.println("-----------> Deleting event");
        eventRepo.deleteById(eventId);
        return true;
    }

    // @Override
    // public boolean updateEvent(EventDto eventDto, Long eventId) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'updateEvent'");
    // }
}
