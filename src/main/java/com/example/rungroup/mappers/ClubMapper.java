package com.example.rungroup.mappers;
import com.example.rungroup.entities.*;

import java.util.stream.Collectors;

import com.example.rungroup.dto.*;

public class ClubMapper {
    public static ClubEntity mapToClub(ClubDto club) {
        ClubEntity clubEnt = ClubEntity.builder()
                .id(club.getId())
                .title(club.getTitle())
                .photoUrl(club.getPhotoUrl())
                .content(club.getContent())
                // .createdBy(userSrv.findById(club.getId()))
                .createdOn(club.getCreatedOn())
                .updatedOn(club.getUpdatedOn())
                // .eventList(

                // )
                .build();
        return  clubEnt;
    }

    public static ClubDto mapToClubDto(ClubEntity club) {
        ClubDto clubDto = ClubDto.builder()
                .id(club.getId())
                .title(club.getTitle())
                .photoUrl(club.getPhotoUrl())
                .content(club.getContent())
                .authorId(club.getCreatedBy().getId())
                .createdOn(club.getCreatedOn())
                .updatedOn(club.getUpdatedOn())
                .eventList(club.getEventList()
                    .stream()
                    .map(event -> EventMapper.mapToEventDto(event))
                    .collect(Collectors.toList()))
                // .eventIdList(club.getEventList()
                //     .stream()
                //     .map(EventEntity::getId)
                //     .collect(Collectors.toList()))
                .build();
        return clubDto;
    }
}
