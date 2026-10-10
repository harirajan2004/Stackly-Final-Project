package com.example.service;

import java.util.List;

import com.example.dto.FarmerRequestDto;
import com.example.dto.FarmerResponseDto;

public interface FarmerService {

    FarmerResponseDto createOrUpdateFarmer(FarmerRequestDto requestDto);

    FarmerResponseDto getFarmerById(Integer farmerId);

    List<FarmerResponseDto> getAllFarmers();

    void deleteFarmer(Integer farmerId);
    FarmerResponseDto getFarmerWithFarms(Integer farmerId);
}