package com.example.rungroup.mappers;

import com.example.rungroup.services.*;

import lombok.AllArgsConstructor;

import com.example.rungroup.entities.*;
import com.example.rungroup.dto.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.ArrayList;

import com.example.rungroup.dto.ClubDto;
import com.example.rungroup.entities.ClubEntity;

// @Component
// @AllArgsConstructor
public class ClubMapper {
    public static ClubEntity mapToClub(ClubDto club) {
        ClubEntity clubDto = ClubEntity.builder()
                .id(club.getId())
                .title(club.getTitle())
                .photoUrl(club.getPhotoUrl())
                .content(club.getContent())
                .createdBy(club.getCreatedBy())
                .createdOn(club.getCreatedOn())
                .updatedOn(club.getUpdatedOn())
                // .eventList(

                // )
                .build();
        return  clubDto;
    }

    public static ClubDto mapToClubDto(ClubEntity club) {
        ClubDto clubDto = ClubDto.builder()
                .id(club.getId())
                .title(club.getTitle())
                .photoUrl(club.getPhotoUrl())
                .content(club.getContent())
                .createdBy(club.getCreatedBy())
                .createdOn(club.getCreatedOn())
                .updatedOn(club.getUpdatedOn())
                .eventList(club.getEventList()
                    .stream()
                    .map(event -> EventMapper.mapToEventDto(event))
                    .collect(Collectors.toList()))
                .build();
        return clubDto;
    }

}
