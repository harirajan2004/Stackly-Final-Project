package com.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FarmerRequestDto {

    private Integer farmerId;

    @NotBlank(message = "Farmer name is required")
    private String farmerName;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
}