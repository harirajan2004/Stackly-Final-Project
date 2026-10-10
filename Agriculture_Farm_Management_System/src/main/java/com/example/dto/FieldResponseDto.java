package com.example.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FieldResponseDto {

    private Integer fieldId;

    private String fieldName;

    private Double area;

    private Integer farmId;
    
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime updatedDate;
    private String updatedBy;
}