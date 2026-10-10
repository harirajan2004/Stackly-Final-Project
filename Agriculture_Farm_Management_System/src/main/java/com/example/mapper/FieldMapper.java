package com.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.dto.FieldRequestDto;
import com.example.dto.FieldResponseDto;
import com.example.entity.Field;

@Mapper(componentModel = "spring")
public interface FieldMapper {

    @Mapping(target = "farm", ignore = true)
    @Mapping(target = "crops", ignore = true)
    @Mapping(target = "activities", ignore = true)
    Field toEntity(FieldRequestDto requestDto);

    @Mapping(source = "farm.farmId", target = "farmId")
    FieldResponseDto toResponseDto(Field field);

    @Mapping(target = "farm", ignore = true)
    @Mapping(target = "crops", ignore = true)
    @Mapping(target = "activities", ignore = true)
    void updateEntity(FieldRequestDto requestDto, @MappingTarget Field field);
}