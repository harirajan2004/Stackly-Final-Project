package com.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.dto.CropRequestDto;
import com.example.dto.CropResponseDto;
import com.example.entity.Crop;

@Mapper(componentModel = "spring")
public interface CropMapper {

    @Mapping(target = "field", ignore = true)
    @Mapping(target = "harvests", ignore = true)
    Crop toEntity(CropRequestDto requestDto);

    @Mapping(source = "field.fieldId", target = "fieldId")
    CropResponseDto toResponseDto(Crop crop);

    @Mapping(target = "field", ignore = true)
    @Mapping(target = "harvests", ignore = true)
    void updateEntity(CropRequestDto requestDto, @MappingTarget Crop crop);
}