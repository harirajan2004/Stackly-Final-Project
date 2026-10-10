package com.example.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class FarmResponseDto {

    private Integer farmId;

    private String farmName;

    private String location;

    private Double totalArea;

    private Integer farmerId;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime createdDate;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String createdBy;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime updatedDate;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String updatedBy;
}