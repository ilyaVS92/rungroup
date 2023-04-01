package com.example.rungroup.dto;

import com.example.rungroup.dto.*;
import com.example.rungroup.entities.UserEntity;

import groovy.transform.ToString;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
@lombok.ToString
public class ClubDto{
    private Long id;
    @NotEmpty(message = "club title should be filled in")
    private String title;
    @NotEmpty(message = "photo URL should be filled in")
    private String photoUrl;
    @NotEmpty(message = "club content should be filled in")
    private String content;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    // private UserEntity createdBy;
    private List<EventDto> eventList;

    // private List<Long> eventIdList;
    private Long authorId;
}
