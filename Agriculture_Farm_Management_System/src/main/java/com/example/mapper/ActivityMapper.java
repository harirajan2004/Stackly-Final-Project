package com.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.dto.ActivityRequestDto;
import com.example.dto.ActivityResponseDto;
import com.example.entity.Activity;

@Mapper(componentModel = "spring")
public interface ActivityMapper {

    @Mapping(target = "field", ignore = true)
    @Mapping(target = "expenses", ignore = true)
    Activity toEntity(ActivityRequestDto requestDto);

    @Mapping(source = "field.fieldId", target = "fieldId")
    ActivityResponseDto toResponseDto(Activity activity);

    @Mapping(target = "field", ignore = true)
    @Mapping(target = "expenses", ignore = true)
    void updateEntity(ActivityRequestDto requestDto, @MappingTarget Activity activity);
}