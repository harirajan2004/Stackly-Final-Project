package com.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.example.dto.FarmerRequestDto;
import com.example.dto.FarmerResponseDto;
import com.example.entity.Farmer;

@Mapper(componentModel = "spring")
public interface FarmerMapper {

    Farmer toEntity(FarmerRequestDto requestDto);

    FarmerResponseDto toResponseDto(Farmer farmer);

    void updateEntity(FarmerRequestDto requestDto, @MappingTarget Farmer farmer);
}
