package com.example.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class HarvestRequestDto {

    private Integer harvestId;

    @NotNull(message = "Harvest date is required")
    private LocalDate harvestDate;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than zero")
    private Double quantity;

    @NotNull(message = "Unit is required")
    private String unit;

    @NotNull(message = "Selling price is required")
    @Positive(message = "Selling price must be greater than zero")
    private Double sellingPrice;

    @NotNull(message = "Crop ID is required")
    private Integer cropId;
}