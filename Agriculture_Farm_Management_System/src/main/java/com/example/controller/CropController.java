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

import com.example.dto.CropRequestDto;
import com.example.dto.CropResponseDto;
import com.example.enums.CropStatus;
import com.example.service.CropService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Crop", description = "Crop management APIs")
public class CropController {

    @Autowired
    private CropService cropService;

    @Operation(
            summary = "Create or update crop",
            description = "Creates a new crop or updates an existing crop")
    @ApiResponse(
            responseCode = "200",
            description = "Crop saved successfully")
    @PostMapping("/api/crop")
    public ResponseEntity<CropResponseDto> createOrUpdateCrop(
            @Valid @RequestBody CropRequestDto requestDto) {

        CropResponseDto response =
                cropService.createOrUpdateCrop(requestDto);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get crop by ID",
            description = "Returns a crop using crop ID")
    @ApiResponse(
            responseCode = "200",
            description = "Crop found successfully")
    @GetMapping("/api/crop/{cropId}")
    public ResponseEntity<CropResponseDto> getCropById(
            @PathVariable Integer cropId) {

        CropResponseDto response =
                cropService.getCropById(cropId);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get all crops",
            description = "Returns all crops")
    @ApiResponse(
            responseCode = "200",
            description = "Crops retrieved successfully")
    @GetMapping("/api/crop")
    public ResponseEntity<List<CropResponseDto>> getAllCrops() {

        List<CropResponseDto> response =
                cropService.getAllCrops();

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get crops by status",
            description = "Returns crops by status with pagination and sorting")
    @ApiResponse(
            responseCode = "200",
            description = "Crops retrieved successfully")
    @GetMapping("/api/crop/status")
    public ResponseEntity<Page<CropResponseDto>> getCropsByStatus(
            @RequestParam CropStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "cropName") String sortBy) {

        Page<CropResponseDto> crops =
                cropService.getCropsByStatus(
                        status,
                        page,
                        size,
                        sortBy);

        return ResponseEntity.ok()
                .header("X-Total-Crops",
                        String.valueOf(crops.getTotalElements()))
                .body(crops);
    }

    @Operation(
            summary = "Get crops by field",
            description = "Returns all crops belonging to a field")
    @ApiResponse(
            responseCode = "200",
            description = "Crops retrieved successfully")
    @GetMapping("/api/crop/field/{fieldId}")
    public ResponseEntity<List<CropResponseDto>> getCropsByField(
            @PathVariable Integer fieldId) {

        List<CropResponseDto> response =
                cropService.getCropsByField(fieldId);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get crops by planting date",
            description = "Returns crops planted between the given dates")
    @ApiResponse(
            responseCode = "200",
            description = "Crops retrieved successfully")
    @GetMapping("/api/crop/planting-date")
    public ResponseEntity<List<CropResponseDto>> getCropsByPlantingDate(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        List<CropResponseDto> response =
                cropService.getCropsByPlantingDate(
                        startDate,
                        endDate);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Delete crop",
            description = "Deletes a crop using crop ID")
    @ApiResponse(
            responseCode = "200",
            description = "Crop deleted successfully")
    @DeleteMapping("/api/crop/{cropId}")
    public ResponseEntity<String> deleteCrop(
            @PathVariable Integer cropId) {

        cropService.deleteCrop(cropId);

        return new ResponseEntity<>(
                "Crop deleted successfully",
                HttpStatus.OK);
    }
}