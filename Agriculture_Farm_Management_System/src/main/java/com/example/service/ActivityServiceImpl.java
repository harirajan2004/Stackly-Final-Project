package com.example.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.constants.Constants;
import com.example.dto.ActivityRequestDto;
import com.example.dto.ActivityResponseDto;
import com.example.entity.Activity;
import com.example.entity.Field;
import com.example.exception.ActivityNotFoundException;
import com.example.exception.FieldNotFoundException;
import com.example.mapper.ActivityMapper;
import com.example.repository.ActivityRepository;
import com.example.repository.FieldRepository;

@Service
public class ActivityServiceImpl implements ActivityService {

    private static final Logger logger =
            LoggerFactory.getLogger(ActivityServiceImpl.class);

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private ActivityMapper activityMapper;

    @Override
    @Transactional
    @CachePut(value = "activities", key = "#result.activityId")
    public ActivityResponseDto createOrUpdateActivity(
            ActivityRequestDto requestDto) {

        logger.info("Creating or updating activity");

        Activity activity;

        if (requestDto.getActivityId() == null) {

            logger.info("Creating new activity");

            activity = activityMapper.toEntity(requestDto);

        } else {

            logger.info(
                    "Updating activity with ID: {}",
                    requestDto.getActivityId());

            activity = activityRepository.findById(
                    requestDto.getActivityId())
                    .orElseThrow(() ->
                            new ActivityNotFoundException(
                                    Constants.ACTIVITY_NOT_FOUND));

            activityMapper.updateEntity(
                    requestDto,
                    activity);
        }

        Field field = fieldRepository.findById(
                requestDto.getFieldId())
                .orElseThrow(() ->
                        new FieldNotFoundException(
                                Constants.FIELD_NOT_FOUND));

        activity.setField(field);

        Activity savedActivity =
                activityRepository.save(activity);

        logger.info(
                "Activity saved successfully with ID: {}",
                savedActivity.getActivityId());

        return activityMapper.toResponseDto(savedActivity);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "activities", key = "#activityId")
    public ActivityResponseDto getActivityById(
            Integer activityId) {

        logger.info(
                "Fetching activity with ID: {}",
                activityId);

        Activity activity =
                activityRepository.findById(activityId)
                        .orElseThrow(() ->
                                new ActivityNotFoundException(
                                        Constants.ACTIVITY_NOT_FOUND));

        logger.info(
                "Activity found with ID: {}",
                activityId);

        return activityMapper.toResponseDto(activity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityResponseDto> getAllActivities() {

        logger.info("Fetching all activities");

        List<ActivityResponseDto> activities =
                activityRepository.findAll()
                        .stream()
                        .map(activityMapper::toResponseDto)
                        .collect(Collectors.toList());

        logger.info(
                "Total activities fetched: {}",
                activities.size());

        return activities;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityResponseDto> getActivitiesByField(
            Integer fieldId,
            int page,
            int size,
            String sortBy) {

        logger.info(
                "Fetching activities for field ID: {}, page: {}, size: {}, sortBy: {}",
                fieldId,
                page,
                size,
                sortBy);

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(sortBy).ascending());

        Page<Activity> activities =
                activityRepository.findByFieldFieldId(
                        fieldId,
                        pageable);

        logger.info(
                "Activities fetched for field ID: {}",
                fieldId);

        return activities.map(
                activityMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityResponseDto> getActivitiesByDate(
            LocalDate startDate,
            LocalDate endDate) {

        logger.info(
                "Fetching activities between dates: {} and {}",
                startDate,
                endDate);

        List<ActivityResponseDto> activities =
                activityRepository
                        .findByActivityDateBetween(
                                startDate,
                                endDate)
                        .stream()
                        .map(activityMapper::toResponseDto)
                        .collect(Collectors.toList());

        logger.info(
                "Total activities fetched between dates: {}",
                activities.size());

        return activities;
    }

    @Override
    @Transactional
    @CacheEvict(value = "activities", key = "#activityId")
    public void deleteActivity(Integer activityId) {

        logger.info(
                "Deleting activity with ID: {}",
                activityId);

        Activity activity =
                activityRepository.findById(activityId)
                        .orElseThrow(() ->
                                new ActivityNotFoundException(
                                        Constants.ACTIVITY_NOT_FOUND));

        activityRepository.delete(activity);

        logger.info(
                "Activity deleted successfully with ID: {}",
                activityId);
    }
}