package com.example.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class FarmerResponseDto {

    private Integer farmerId;

    private String farmerName;

    private String email;

    private String phoneNumber;
   
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime createdDate;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String createdBy;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime updatedDate;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String updatedBy;
    
    private List<FarmResponseDto> farms;
}