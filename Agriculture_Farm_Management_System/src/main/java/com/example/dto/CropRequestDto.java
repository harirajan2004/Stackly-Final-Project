package com.example.dto;

import java.time.LocalDate;

import com.example.enums.CropStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CropRequestDto {

    private Integer cropId;

    @NotBlank(message = "Crop name is required")
    private String cropName;

    @NotNull(message = "Planting date is required")
    private LocalDate plantingDate;

    @NotNull(message = "Expected harvest date is required")
    private LocalDate expectedHarvestDate;
    
    @NotNull(message = "Crop status is required")
    private CropStatus status;
    
    @NotNull(message = "Field ID is required")
    private Integer fieldId;
}