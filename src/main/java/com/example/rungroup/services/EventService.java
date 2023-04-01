package com.example.rungroup.services;

import java.util.List;

import javax.validation.Valid;

import com.example.rungroup.dto.EventDto;
import com.example.rungroup.entities.EventEntity;

public interface EventService {
    List<EventEntity> findAllByClubId (Long id);
    List<EventDto> findAllEvents();
    EventDto findById(Long id);
    boolean save(EventDto event, Long clubId);
    boolean updateEvent(EventDto eventDto, Long eventId, Long clubId);
    boolean delete(Long eventId);
    boolean userHasAuthority(Long eventId);
}
