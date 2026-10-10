package com.example.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.dto.FarmRequestDto;
import com.example.dto.FarmResponseDto;

public interface FarmService {

    FarmResponseDto createOrUpdateFarm(FarmRequestDto requestDto);

    FarmResponseDto getFarmById(Integer farmId);

    List<FarmResponseDto> getAllFarms();

    Page<FarmResponseDto> getFarmsByFarmer(
            Integer farmerId, int page, int size, String sortBy);

    void deleteFarm(Integer farmId);
    
    
}