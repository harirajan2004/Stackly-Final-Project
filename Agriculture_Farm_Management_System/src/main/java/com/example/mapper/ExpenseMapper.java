package com.example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.dto.ExpenseRequestDto;
import com.example.dto.ExpenseResponseDto;
import com.example.entity.Expense;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    @Mapping(target = "activity", ignore = true)
    Expense toEntity(ExpenseRequestDto requestDto);

    @Mapping(source = "activity.activityId", target = "activityId")
    ExpenseResponseDto toResponseDto(Expense expense);

    @Mapping(target = "activity", ignore = true)
    void updateEntity(ExpenseRequestDto requestDto, @MappingTarget Expense expense);
}