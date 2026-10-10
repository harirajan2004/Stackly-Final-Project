package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class FieldRequestDto {

    private Integer fieldId;

    @NotBlank(message = "Field name is required")
    private String fieldName;

    @NotNull(message = "Area is required")
    @Positive(message = "Area must be greater than zero")
    private Double area;

    @NotNull(message = "Farm ID is required")
    private Integer farmId;
}
