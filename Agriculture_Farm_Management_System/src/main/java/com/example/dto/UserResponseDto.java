package com.example.dto;

import lombok.Data;

@Data
public class UserResponseDto {

    private Integer userId;
    private String username;
    private String role;
}