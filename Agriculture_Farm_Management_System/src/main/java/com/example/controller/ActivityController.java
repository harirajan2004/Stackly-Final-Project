package com.example.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.ActivityRequestDto;
import com.example.dto.ActivityResponseDto;
import com.example.service.ActivityService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Activity", description = "Activity management APIs")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Operation(
            summary = "Create or update activity",
            description = "Creates a new activity or updates an existing activity")
    @ApiResponse(
            responseCode = "200",
            description = "Activity saved successfully")
    @PostMapping("/api/activity")
    public ResponseEntity<ActivityResponseDto> createOrUpdateActivity(
            @Valid @RequestBody ActivityRequestDto requestDto) {

        ActivityResponseDto response =
                activityService.createOrUpdateActivity(requestDto);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get activity by ID",
            description = "Returns an activity using activity ID")
    @ApiResponse(
            responseCode = "200",
            description = "Activity found successfully")
    @GetMapping("/api/activity/{activityId}")
    public ResponseEntity<ActivityResponseDto> getActivityById(
            @PathVariable Integer activityId) {

        ActivityResponseDto response =
                activityService.getActivityById(activityId);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get all activities",
            description = "Returns all activities")
    @ApiResponse(
            responseCode = "200",
            description = "Activities retrieved successfully")
    @GetMapping("/api/activity")
    public ResponseEntity<List<ActivityResponseDto>> getAllActivities() {

        List<ActivityResponseDto> response =
                activityService.getAllActivities();

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get activities by field",
            description = "Returns activities belonging to a field with pagination and sorting")
    @ApiResponse(
            responseCode = "200",
            description = "Activities retrieved successfully")
    @GetMapping("/api/activity/field/{fieldId}")
    public ResponseEntity<Page<ActivityResponseDto>> getActivitiesByField(
            @PathVariable Integer fieldId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "activityDate") String sortBy) {

        Page<ActivityResponseDto> activities =
                activityService.getActivitiesByField(
                        fieldId,
                        page,
                        size,
                        sortBy);

        return ResponseEntity.ok()
                .header("X-Total-Activities",
                        String.valueOf(activities.getTotalElements()))
                .body(activities);
    }

    @Operation(
            summary = "Get activities by date",
            description = "Returns activities between the given dates")
    @ApiResponse(
            responseCode = "200",
            description = "Activities retrieved successfully")
    @GetMapping("/api/activity/date")
    public ResponseEntity<List<ActivityResponseDto>> getActivitiesByDate(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        List<ActivityResponseDto> response =
                activityService.getActivitiesByDate(
                        startDate,
                        endDate);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Delete activity",
            description = "Deletes an activity using activity ID")
    @ApiResponse(
            responseCode = "200",
            description = "Activity deleted successfully")
    @DeleteMapping("/api/activity/{activityId}")
    public ResponseEntity<String> deleteActivity(
            @PathVariable Integer activityId) {

        activityService.deleteActivity(activityId);

        return new ResponseEntity<>(
                "Activity deleted successfully",
                HttpStatus.OK);
    }
}