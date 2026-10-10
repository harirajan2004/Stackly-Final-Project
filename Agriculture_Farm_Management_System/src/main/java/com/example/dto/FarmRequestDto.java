package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class FarmRequestDto {

    private Integer farmId;

    @NotBlank(message = "Farm name is required")
    private String farmName;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Total area is required")
    @Positive(message = "Total area must be greater than zero")
    private Double totalArea;

    @NotNull(message = "Farmer ID is required")
    private Integer farmerId;
}