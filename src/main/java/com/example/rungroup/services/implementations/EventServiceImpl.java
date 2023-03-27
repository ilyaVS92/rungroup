package com.example.rungroup.services.implementations;

import org.springframework.stereotype.Service;
import com.example.rungroup.entities.*;
import com.example.rungroup.mappers.EventMapper;
import com.example.rungroup.dto.EventDto;
import com.example.rungroup.repos.ClubRepo;
import com.example.rungroup.repos.EventRepo;
import com.example.rungroup.services.ClubService;
import com.example.rungroup.services.EventService;
import com.example.rungroup.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService{
    
    private ClubRepo clubRepo;
    private EventRepo eventRepo;
    private ClubService clubSrv;
    private UserService userSrv;

    @Override
    public void createEvent(EventDto eventDto, Long id) {
        Event event = EventMapper.mapToEvent(eventDto);
        event.setClub(clubRepo.findById(id).orElseThrow());
        // event.setCreatedBy(userSrv.getCurrentUserEntity());
        eventRepo.save(event);
    }

    @Override
    public List<Event> findAllByClubId(Long id){
        return eventRepo.findAllByClubId(id);
    }

    @Override
    public List<EventDto> findAllEvents() {
        List<Event> events = eventRepo.findAll();
        return events.stream()
            .map(event -> EventMapper.mapToEventDto(event))
            .collect(Collectors.toList());
    }

    @Override
    public EventDto findById(Long id) {
        return EventMapper.mapToEventDto(eventRepo.findById(id).orElseThrow());
    }

    @Override
    public void save(@Valid EventDto event) {
        eventRepo.save(EventMapper.mapToEvent(event).);
        System.out.println("Status OK: save successful");
    }

    @Override
    public void updateEvent(EventDto eventDto, Long eventId, Long clubId) {
        eventDto.setClub(clubSrv.findEntityById(clubId));
        eventDto.setId(eventId);
        eventRepo.save(EventMapper.mapToEvent(eventDto));

    }

    @Override
    public void delete(Long eventId) {
        eventRepo.deleteById(eventId);
        System.out.println("=====>DELETING<===== event with ID="+eventId);
    }
}
