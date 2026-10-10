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

import com.example.dto.FieldRequestDto;
import com.example.dto.FieldResponseDto;
import com.example.service.FieldService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Field", description = "Field management APIs")
public class FieldController {

    @Autowired
    private FieldService fieldService;

    @Operation(
            summary = "Create or update field",
            description = "Creates a new field or updates an existing field")
    @ApiResponse(
            responseCode = "200",
            description = "Field saved successfully")
    @PostMapping("/api/field")
    public ResponseEntity<FieldResponseDto> createOrUpdateField(
            @Valid @RequestBody FieldRequestDto requestDto) {

        FieldResponseDto response =
                fieldService.createOrUpdateField(requestDto);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get field by ID",
            description = "Returns a field using field ID")
    @ApiResponse(
            responseCode = "200",
            description = "Field found successfully")
    @GetMapping("/api/field/{fieldId}")
    public ResponseEntity<FieldResponseDto> getFieldById(
            @PathVariable Integer fieldId) {

        FieldResponseDto response =
                fieldService.getFieldById(fieldId);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get all fields",
            description = "Returns all fields")
    @ApiResponse(
            responseCode = "200",
            description = "Fields retrieved successfully")
    @GetMapping("/api/field")
    public ResponseEntity<List<FieldResponseDto>> getAllFields() {

        List<FieldResponseDto> response =
                fieldService.getAllFields();

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get fields by farm",
            description = "Returns fields belonging to a farm with pagination and sorting")
    @ApiResponse(
            responseCode = "200",
            description = "Fields retrieved successfully")
    @GetMapping("/api/field/farm/{farmId}")
    public ResponseEntity<Page<FieldResponseDto>> getFieldsByFarm(
            @PathVariable Integer farmId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "fieldName") String sortBy) {

        Page<FieldResponseDto> fields =
                fieldService.getFieldsByFarm(
                        farmId,
                        page,
                        size,
                        sortBy);

        return ResponseEntity.ok()
                .header("X-Total-Fields",
                        String.valueOf(fields.getTotalElements()))
                .body(fields);
    }

    @Operation(
            summary = "Delete field",
            description = "Deletes a field using field ID")
    @ApiResponse(
            responseCode = "200",
            description = "Field deleted successfully")
    @DeleteMapping("/api/field/{fieldId}")
    public ResponseEntity<String> deleteField(
            @PathVariable Integer fieldId) {

        fieldService.deleteField(fieldId);

        return new ResponseEntity<>(
                "Field deleted successfully",
                HttpStatus.OK);
    }
}