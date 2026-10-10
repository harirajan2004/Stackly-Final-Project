package com.example.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.dto.FieldRequestDto;
import com.example.dto.FieldResponseDto;

public interface FieldService {

    FieldResponseDto createOrUpdateField(FieldRequestDto requestDto);

    FieldResponseDto getFieldById(Integer fieldId);

    List<FieldResponseDto> getAllFields();

    Page<FieldResponseDto> getFieldsByFarm(
            Integer farmId, int page, int size, String sortBy);

    void deleteField(Integer fieldId);
}