package com.example.controller;

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

import com.example.dto.FarmRequestDto;
import com.example.dto.FarmResponseDto;
import com.example.service.FarmService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Farm", description = "Farm management APIs")
public class FarmController {

    @Autowired
    private FarmService farmService;

    @Operation(
            summary = "Create or update farm",
            description = "Creates a new farm or updates an existing farm")
    @ApiResponse(
            responseCode = "200",
            description = "Farm saved successfully")
    @PostMapping("/api/farm")
    public ResponseEntity<FarmResponseDto> createOrUpdateFarm(
            @Valid @RequestBody FarmRequestDto requestDto) {

        FarmResponseDto response =
                farmService.createOrUpdateFarm(requestDto);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get farm by ID",
            description = "Returns a farm using farm ID")
    @ApiResponse(
            responseCode = "200",
            description = "Farm found successfully")
    @GetMapping("/api/farm/{farmId}")
    public ResponseEntity<FarmResponseDto> getFarmById(
            @PathVariable Integer farmId) {

        FarmResponseDto response =
                farmService.getFarmById(farmId);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get all farms",
            description = "Returns all farms")
    @ApiResponse(
            responseCode = "200",
            description = "Farms retrieved successfully")
    @GetMapping("/api/farm")
    public ResponseEntity<List<FarmResponseDto>> getAllFarms() {

        List<FarmResponseDto> response =
                farmService.getAllFarms();

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get farms by farmer",
            description = "Returns farms belonging to a farmer with pagination and sorting")
    @ApiResponse(
            responseCode = "200",
            description = "Farms retrieved successfully")
    @GetMapping("/api/farm/farmer/{farmerId}")
    public ResponseEntity<Page<FarmResponseDto>> getFarmsByFarmer(
            @PathVariable Integer farmerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "farmName") String sortBy) {

        Page<FarmResponseDto> farms =
                farmService.getFarmsByFarmer(
                        farmerId,
                        page,
                        size,
                        sortBy);

        return ResponseEntity.ok()
                .header("X-Total-Farms",
                        String.valueOf(farms.getTotalElements()))
                .body(farms);
    }

    @Operation(
            summary = "Delete farm",
            description = "Deletes a farm using farm ID")
    @ApiResponse(
            responseCode = "200",
            description = "Farm deleted successfully")
    @DeleteMapping("/api/farm/{farmId}")
    public ResponseEntity<String> deleteFarm(
            @PathVariable Integer farmId) {

        farmService.deleteFarm(farmId);

        return new ResponseEntity<>(
                "Farm deleted successfully",
                HttpStatus.OK);
    }
}