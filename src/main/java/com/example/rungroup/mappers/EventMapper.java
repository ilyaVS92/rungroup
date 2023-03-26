package com.example.rungroup.mappers;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


import com.example.rungroup.dto.EventDto;
import com.example.rungroup.entities.Event;
import com.example.rungroup.entities.Event.EventBuilder;

import org.springframework.context.annotation.Bean;


import lombok.AllArgsConstructor;

// @Component
// @AllArgsConstructor
public class EventMapper {

    public static Event mapToEvent(EventDto eventDto) {
        Event event = new Event();
        event.setId(eventDto.getId());
        event.setName(eventDto.getName());
        event.setStartTime(eventDto.getStartTime());
        event.setEndTime(eventDto.getEndTime());
        event.setType(eventDto.getType());
        event.setPhotoUrl(eventDto.getPhotoUrl());
        event.setCreatedOn(eventDto.getCreatedOn());
        event.setUpdatedOn(eventDto.getUpdatedOn());
        event.setClub(eventDto.getClub());
        return event;
    }


    public static EventDto mapToEventDto (Event event){
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setName(event.getName());
        eventDto.setStartTime(event.getStartTime());
        eventDto.setEndTime(event.getEndTime());
        eventDto.setType(event.getType());
        eventDto.setPhotoUrl(event.getPhotoUrl());
        eventDto.setCreatedOn(event.getCreatedOn());
        eventDto.setUpdatedOn(event.getUpdatedOn());
        eventDto.setClub(event.getClub());
        return eventDto;
    }
}
