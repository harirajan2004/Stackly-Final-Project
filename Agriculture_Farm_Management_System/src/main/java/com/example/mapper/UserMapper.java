package com.example.mapper;

import org.mapstruct.Mapper;

import com.example.dto.UserRequestDto;
import com.example.dto.UserResponseDto;
import com.example.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequestDto requestDto);

    UserResponseDto toResponseDto(User user);
}