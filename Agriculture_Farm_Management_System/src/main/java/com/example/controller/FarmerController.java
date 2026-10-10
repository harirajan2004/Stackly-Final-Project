package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.FarmerRequestDto;
import com.example.dto.FarmerResponseDto;
import com.example.service.FarmerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Farmer", description = "Farmer management APIs")
public class FarmerController {

    @Autowired
    private FarmerService farmerService;

    @Operation(
            summary = "Create or update farmer",
            description = "Creates a new farmer or updates an existing farmer")
    @ApiResponse(
            responseCode = "200",
            description = "Farmer saved successfully")
    @PostMapping("/api/farmer")
    public ResponseEntity<FarmerResponseDto> createOrUpdateFarmer(
            @Valid @RequestBody FarmerRequestDto requestDto) {

        FarmerResponseDto response =
                farmerService.createOrUpdateFarmer(requestDto);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get farmer by ID",
            description = "Returns a farmer using farmer ID")
    @ApiResponse(
            responseCode = "200",
            description = "Farmer found successfully")
    @GetMapping("/api/farmer/{farmerId}")
    public ResponseEntity<FarmerResponseDto> getFarmerById(
            @PathVariable Integer farmerId) {

        FarmerResponseDto response =
                farmerService.getFarmerById(farmerId);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get all farmers",
            description = "Returns all farmers")
    @ApiResponse(
            responseCode = "200",
            description = "Farmers retrieved successfully")
    @GetMapping("/api/farmer")
    public ResponseEntity<List<FarmerResponseDto>> getAllFarmers() {

        List<FarmerResponseDto> response =
                farmerService.getAllFarmers();

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Delete farmer",
            description = "Deletes a farmer using farmer ID")
    @ApiResponse(
            responseCode = "200",
            description = "Farmer deleted successfully")
    @DeleteMapping("/api/farmer/{farmerId}")
    public ResponseEntity<String> deleteFarmer(
            @PathVariable Integer farmerId) {

        farmerService.deleteFarmer(farmerId);

        return new ResponseEntity<>(
                "Farmer deleted successfully",
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get farmer details with farms",
            description = "Returns farmer profile along with a list of all associated farms")
    @ApiResponse(
            responseCode = "200",
            description = "Farmer details with farms retrieved successfully")
    @GetMapping("/api/farmer/{farmerId}/details")
    public ResponseEntity<FarmerResponseDto> getFarmerDetails(
            @PathVariable Integer farmerId) {

        FarmerResponseDto response =
                farmerService.getFarmerWithFarms(farmerId);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }
}