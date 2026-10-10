package com.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.dto.HarvestRequestDto;
import com.example.dto.HarvestResponseDto;
import com.example.entity.Harvest;

@Mapper(componentModel = "spring")
public interface HarvestMapper {

    @Mapping(target = "crop", ignore = true)
    Harvest toEntity(HarvestRequestDto requestDto);

    @Mapping(source = "crop.cropId", target = "cropId")
    HarvestResponseDto toResponseDto(Harvest harvest);

    @Mapping(target = "crop", ignore = true)
    void updateEntity(HarvestRequestDto requestDto, @MappingTarget Harvest harvest);
}