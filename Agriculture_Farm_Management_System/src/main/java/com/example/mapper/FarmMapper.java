package com.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.dto.FarmRequestDto;
import com.example.dto.FarmResponseDto;
import com.example.entity.Farm;

@Mapper(componentModel = "spring")
public interface FarmMapper {

    @Mapping(target = "farmer", ignore = true)
    @Mapping(target = "fields", ignore = true)
    Farm toEntity(FarmRequestDto requestDto);

    @Mapping(source = "farmer.farmerId", target = "farmerId")
    FarmResponseDto toResponseDto(Farm farm);

    @Mapping(target = "farmer", ignore = true)
    @Mapping(target = "fields", ignore = true)
    void updateEntity(FarmRequestDto requestDto, @MappingTarget Farm farm);
}