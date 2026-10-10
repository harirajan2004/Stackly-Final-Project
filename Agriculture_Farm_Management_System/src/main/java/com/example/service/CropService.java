package com.example.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

import com.example.dto.CropRequestDto;
import com.example.dto.CropResponseDto;
import com.example.enums.CropStatus;

public interface CropService {

    CropResponseDto createOrUpdateCrop(CropRequestDto requestDto);

    CropResponseDto getCropById(Integer cropId);

    List<CropResponseDto> getAllCrops();

    Page<CropResponseDto> getCropsByStatus(
            CropStatus status, int page, int size, String sortBy);

    List<CropResponseDto> getCropsByField(Integer fieldId);

    List<CropResponseDto> getCropsByPlantingDate(
            LocalDate startDate, LocalDate endDate);

    void deleteCrop(Integer cropId);
}