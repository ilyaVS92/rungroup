package com.example.rungroup.mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.rungroup.dto.*;
import com.example.rungroup.entities.*;
import com.example.rungroup.services.*;

@Component
public class EventMapper {
    // private ClubService clubSrv;
    // private UserService userSrv;

    public static EventEntity mapToEvent(EventDto eventDto) {
        // event.setClub(clubSrv.findEntityById(eventDto.getClubId()));
        // event.setCreatedBy(userSrv.findById(eventDto.getAuthorId()));

        return EventEntity.builder()
        .id(eventDto.getId())
        .name(eventDto.getName())
        .startTime(eventDto.getStartTime())
        .endTime(eventDto.getEndTime())
        .type(eventDto.getType())
        .photoUrl(eventDto.getPhotoUrl())
        .createdOn(eventDto.getCreatedOn())
        .updatedOn(eventDto.getUpdatedOn())
        .club(eventDto.getClub())
        // .createdBy(eventDto.getCreatedBy())
        .build();
    }

    public static EventDto mapToEventDto (EventEntity event){
        // System.out.println("---------------> EventMapper.mapToDto: event="+event.toString());
        // // eventDto.setClubId(event.getClub().getId());
        // // eventDto.setAuthorId(event.getCreatedBy().getId());

        return EventDto.builder()
        .id(event.getId())
        .name(event.getName())
        .startTime(event.getStartTime())
        .endTime(event.getEndTime())
        .type(event.getType())
        .photoUrl(event.getPhotoUrl())
        .createdOn(event.getCreatedOn())
        .updatedOn(event.getUpdatedOn())
        .club(event.getClub())
        // .createdBy(event.getCreatedBy())
        .build();
    }
}
