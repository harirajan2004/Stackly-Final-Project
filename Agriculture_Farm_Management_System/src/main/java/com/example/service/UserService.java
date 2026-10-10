package com.example.service;

import com.example.dto.UserRequestDto;
import com.example.dto.UserResponseDto;

public interface UserService {

    UserResponseDto createUser(UserRequestDto requestDto);
    
    UserResponseDto getUserById(Integer id);
}