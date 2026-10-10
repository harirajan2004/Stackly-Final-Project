package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.UserRequestDto;
import com.example.dto.UserResponseDto;
import com.example.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "User", description = "User registration APIs")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/user")
    @Operation(
            summary = "Register a new user",
            description = "Creates a new user and securely encrypts the password"
    )
    @ApiResponse(
            responseCode = "201",
            description = "User registered successfully"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid user details"
    )
    public ResponseEntity<UserResponseDto> createUser(
            @Valid @RequestBody UserRequestDto requestDto) {

        UserResponseDto response = userService.createUser(requestDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/api/user/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Integer id) {
        UserResponseDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

}