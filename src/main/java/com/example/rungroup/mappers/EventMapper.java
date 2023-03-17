package com.example.rungroup.mappers;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


import com.example.rungroup.dto.EventDto;
import com.example.rungroup.entities.Event;
import org.springframework.context.annotation.Bean;


import lombok.AllArgsConstructor;

// @Component
// @AllArgsConstructor
public class EventMapper {

    public static Event mapToEvent(EventDto eventDto) {
        return Event.builder()
            .id(eventDto.getId())
            .name(eventDto.getName())
            .startTime(eventDto.getStartTime())
            .endTime(eventDto.getEndTime())
            .type(eventDto.getType())
            .photoUrl(eventDto.getPhotoUrl())
            .createdOn(eventDto.getCreatedOn())
            .updatedOn(eventDto.getUpdatedOn())
            // .club(ClubMapper.mapToClub(eventDto.getClub()))
            .build();
    }


    public static EventDto mapToEventDto (Event event){
        return EventDto.builder()
            .id(event.getId())
            .name(event.getName())
            .startTime(event.getStartTime())
            .endTime(event.getEndTime())
            .type(event.getType())
            .photoUrl(event.getPhotoUrl())
            .createdOn(event.getCreatedOn())
            .updatedOn(event.getUpdatedOn())
            // .club(ClubMapper.mapToClubDto(event.getClub()))
            .build();
    }
}
