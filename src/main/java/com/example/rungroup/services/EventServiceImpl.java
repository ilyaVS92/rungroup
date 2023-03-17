package com.example.rungroup.services;

import org.springframework.stereotype.Service;
import com.example.rungroup.entities.*;
import com.example.rungroup.mappers.EventMapper;
import com.example.rungroup.dto.EventDto;
import com.example.rungroup.repos.ClubRepo;
import com.example.rungroup.repos.EventRepo;
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

    @Override
    public void createEvent(EventDto eventDto, Long id) {
        
        Event event = EventMapper.mapToEvent(eventDto);
        event.setClub(clubRepo.findById(id).orElseThrow());
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
        eventRepo.save(EventMapper.mapToEvent(event));
        System.out.println("Status OK: save successful");
    }



}
