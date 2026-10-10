package com.example.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.enums.ActivityType;

import lombok.Data;

@Data
public class ActivityResponseDto {

    private Integer activityId;

    private ActivityType activityType;

    private LocalDate activityDate;

    private String description;

    private Integer fieldId;
    
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime updatedDate;
    private String updatedBy;
}