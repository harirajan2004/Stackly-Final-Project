package com.example.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

import com.example.dto.ActivityRequestDto;
import com.example.dto.ActivityResponseDto;

public interface ActivityService {

    ActivityResponseDto createOrUpdateActivity(
            ActivityRequestDto requestDto);

    ActivityResponseDto getActivityById(Integer activityId);

    List<ActivityResponseDto> getAllActivities();

    Page<ActivityResponseDto> getActivitiesByField(
            Integer fieldId, int page, int size, String sortBy);

    List<ActivityResponseDto> getActivitiesByDate(
            LocalDate startDate, LocalDate endDate);

    void deleteActivity(Integer activityId);
}