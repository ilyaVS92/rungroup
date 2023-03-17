package com.example.rungroup.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.*;
import lombok.Builder;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class EventDto {
    private Long id;
    private String name;
    private String type;
    private String photoUrl;
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endTime;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    private ClubDto club;

}