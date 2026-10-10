
package com.example.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.example.dto.HarvestRequestDto;
import com.example.dto.HarvestResponseDto;

public interface HarvestService {

    HarvestResponseDto createOrUpdateHarvest(
            HarvestRequestDto requestDto);

    HarvestResponseDto getHarvestById(Integer harvestId);

    List<HarvestResponseDto> getAllHarvests();

    Page<HarvestResponseDto> getHarvestsByCrop(
            Integer cropId, int page, int size, String sortBy);

    List<HarvestResponseDto> getHarvestsByDate(
            LocalDate startDate, LocalDate endDate);

    Double getTotalHarvestQuantity();

    Double getTotalHarvestQuantityBetweenDates(
            LocalDate startDate, LocalDate endDate);

    List<Map<String, Object>> getHarvestReport();

    void deleteHarvest(Integer harvestId);
}
