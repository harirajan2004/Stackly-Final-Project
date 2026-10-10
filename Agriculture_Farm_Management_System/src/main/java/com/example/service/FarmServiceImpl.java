package com.example.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.constants.Constants;
import com.example.dto.FarmRequestDto;
import com.example.dto.FarmResponseDto;
import com.example.entity.Farm;
import com.example.entity.Farmer;
import com.example.exception.FarmNotFoundException;
import com.example.exception.FarmerNotFoundException;
import com.example.mapper.FarmMapper;
import com.example.repository.FarmRepository;
import com.example.repository.FarmerRepository;

@Service
public class FarmServiceImpl implements FarmService {

    private static final Logger logger =
            LoggerFactory.getLogger(FarmServiceImpl.class);

    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private FarmerRepository farmerRepository;

    @Autowired
    private FarmMapper farmMapper;

    @Override
    @Transactional
    @CachePut(value = "farms", key = "#result.farmId")
    public FarmResponseDto createOrUpdateFarm(
            FarmRequestDto requestDto) {

        logger.info("Creating or updating farm");

        Farm farm;

        if (requestDto.getFarmId() == null) {

            logger.info("Creating new farm");

            farm = farmMapper.toEntity(requestDto);

        } else {

            logger.info(
                    "Updating farm with ID: {}",
                    requestDto.getFarmId());

            farm = farmRepository.findById(
                    requestDto.getFarmId())
                    .orElseThrow(() ->
                            new FarmNotFoundException(
                                    Constants.FARM_NOT_FOUND));

            farmMapper.updateEntity(
                    requestDto,
                    farm);
        }

        Farmer farmer = farmerRepository.findById(
                requestDto.getFarmerId())
                .orElseThrow(() ->
                        new FarmerNotFoundException(
                                Constants.FARMER_NOT_FOUND));

        farm.setFarmer(farmer);

        Farm savedFarm =
                farmRepository.save(farm);

        logger.info(
                "Farm saved successfully with ID: {}",
                savedFarm.getFarmId());

        return farmMapper.toResponseDto(savedFarm);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "farms", key = "#farmId")
    public FarmResponseDto getFarmById(
            Integer farmId) {

        logger.info(
                "Fetching farm with ID: {}",
                farmId);

        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() ->
                        new FarmNotFoundException(
                                Constants.FARM_NOT_FOUND));

        logger.info(
                "Farm found with ID: {}",
                farmId);

        return farmMapper.toResponseDto(farm);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FarmResponseDto> getAllFarms() {

        logger.info("Fetching all farms");

        List<FarmResponseDto> farms =
                farmRepository.findAll()
                        .stream()
                        .map(farmMapper::toResponseDto)
                        .collect(Collectors.toList());

        logger.info(
                "Total farms fetched: {}",
                farms.size());

        return farms;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FarmResponseDto> getFarmsByFarmer(
            Integer farmerId,
            int page,
            int size,
            String sortBy) {

        logger.info(
                "Fetching farms for farmer ID: {}, page: {}, size: {}, sortBy: {}",
                farmerId,
                page,
                size,
                sortBy);

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(sortBy).ascending());

        Page<Farm> farms =
                farmRepository.findByFarmerFarmerId(
                        farmerId,
                        pageable);

        logger.info(
                "Farms fetched for farmer ID: {}",
                farmerId);

        return farms.map(farmMapper::toResponseDto);
    }

    @Override
    @Transactional
    @CacheEvict(value = "farms", key = "#farmId")
    public void deleteFarm(Integer farmId) {

        logger.info(
                "Deleting farm with ID: {}",
                farmId);

        Farm farm = farmRepository.findById(farmId)
                .orElseThrow(() ->
                        new FarmNotFoundException(
                                Constants.FARM_NOT_FOUND));

        farmRepository.delete(farm);

        logger.info(
                "Farm deleted successfully with ID: {}",
                farmId);
    }
}