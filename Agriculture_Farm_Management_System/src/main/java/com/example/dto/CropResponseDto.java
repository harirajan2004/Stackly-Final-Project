package com.example.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.enums.CropStatus;

import lombok.Data;

@Data
public class CropResponseDto {

    private Integer cropId;

    private String cropName;

    private LocalDate plantingDate;

    private LocalDate expectedHarvestDate;

    private CropStatus status;

    private Integer fieldId;
    
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime updatedDate;
    private String updatedBy;
}
