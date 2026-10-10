package com.example.dto;

import java.time.LocalDate;

import com.example.enums.ActivityType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ActivityRequestDto {

    private Integer activityId;

    @NotNull(message = "Activity type is required")
    private ActivityType activityType;

    @NotNull(message = "Activity date is required")
    private LocalDate activityDate;

    private String description;

    @NotNull(message = "Field ID is required")
    private Integer fieldId;
}