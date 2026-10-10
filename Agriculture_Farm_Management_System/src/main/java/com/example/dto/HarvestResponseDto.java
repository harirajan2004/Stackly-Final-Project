package com.example.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class HarvestResponseDto {

    private Integer harvestId;

    private LocalDate harvestDate;

    private Double quantity;

    private String unit;

    private Double sellingPrice;

    private Integer cropId;
    
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime updatedDate;
    private String updatedBy;
}