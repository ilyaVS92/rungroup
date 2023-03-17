package com.example.rungroup.services;

import java.util.List;

import javax.validation.Valid;

import com.example.rungroup.dto.EventDto;
import com.example.rungroup.entities.Event;

public interface EventService {
    void createEvent (EventDto eventDto, Long clubId);
    List<Event> findAllByClubId (Long id);
    List<EventDto> findAllEvents();
    EventDto findById(Long id);
    void save(@Valid EventDto event);
}
