package com.example.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.constants.Constants;
import com.example.dto.FarmResponseDto;
import com.example.dto.FarmerRequestDto;
import com.example.dto.FarmerResponseDto;
import com.example.entity.Farm;
import com.example.entity.Farmer;
import com.example.exception.FarmerNotFoundException;
import com.example.mapper.FarmerMapper;
import com.example.repository.FarmRepository;
import com.example.repository.FarmerRepository;

@Service
public class FarmerServiceImpl implements FarmerService {

    private static final Logger logger =
            LoggerFactory.getLogger(FarmerServiceImpl.class);

    @Autowired
    private FarmerRepository farmerRepository;

    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private FarmerMapper farmerMapper;

    @Override
    @Transactional
    @CachePut(value = "farmers", key = "#result.farmerId")
    public FarmerResponseDto createOrUpdateFarmer(
            FarmerRequestDto requestDto) {

        logger.info("Creating or updating farmer");

        Farmer farmer;

        if (requestDto.getFarmerId() == null) {

            logger.info("Creating new farmer");

            farmer = farmerMapper.toEntity(requestDto);

        } else {

            logger.info(
                    "Updating farmer with ID: {}",
                    requestDto.getFarmerId());

            farmer = farmerRepository.findById(
                    requestDto.getFarmerId())
                    .orElseThrow(() ->
                            new FarmerNotFoundException(
                                    Constants.FARMER_NOT_FOUND));

            farmerMapper.updateEntity(
                    requestDto,
                    farmer);
        }

        Farmer savedFarmer =
                farmerRepository.save(farmer);

        logger.info(
                "Farmer saved successfully with ID: {}",
                savedFarmer.getFarmerId());

        return farmerMapper.toResponseDto(savedFarmer);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "farmers", key = "#farmerId")
    public FarmerResponseDto getFarmerById(
            Integer farmerId) {

        logger.info(
                "Fetching farmer with ID: {}",
                farmerId);

        Farmer farmer = farmerRepository.findById(farmerId)
                .orElseThrow(() ->
                        new FarmerNotFoundException(
                                Constants.FARMER_NOT_FOUND));

        logger.info(
                "Farmer found with ID: {}",
                farmerId);

        return farmerMapper.toResponseDto(farmer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FarmerResponseDto> getAllFarmers() {

        logger.info("Fetching all farmers");

        List<FarmerResponseDto> farmers =
                farmerRepository.findAll()
                        .stream()
                        .map(farmerMapper::toResponseDto)
                        .collect(Collectors.toList());

        logger.info(
                "Total farmers fetched: {}",
                farmers.size());

        return farmers;
    }

    @Override
    @Transactional
    @CacheEvict(value = "farmers", key = "#farmerId")
    public void deleteFarmer(Integer farmerId) {

        logger.info(
                "Deleting farmer with ID: {}",
                farmerId);

        Farmer farmer = farmerRepository.findById(farmerId)
                .orElseThrow(() ->
                        new FarmerNotFoundException(
                                Constants.FARMER_NOT_FOUND));

        farmerRepository.delete(farmer);

        logger.info(
                "Farmer deleted successfully with ID: {}",
                farmerId);
    }

    @Override
    @Transactional(readOnly = true)
    public FarmerResponseDto getFarmerWithFarms(Integer farmerId) {
        logger.info("Fetching farmer details with farms for ID: {}", farmerId);

        Farmer farmer = farmerRepository.findById(farmerId)
                .orElseThrow(() ->
                        new FarmerNotFoundException(
                                Constants.FARMER_NOT_FOUND));

        // Fetch all farms for this farmer ID using FarmRepository
        List<Farm> farms = farmRepository.findByFarmerId(farmerId);

        // Map Farmer entity to FarmerResponseDto
        FarmerResponseDto response = new FarmerResponseDto();
        response.setFarmerId(farmer.getFarmerId());
        response.setFarmerName(farmer.getFarmerName());
        response.setEmail(farmer.getEmail());
        response.setPhoneNumber(farmer.getPhoneNumber());

        // Map Farm entities to FarmResponseDto list with safe entity relationship mapping
        List<FarmResponseDto> farmDtos = farms.stream().map(farm -> {
            FarmResponseDto dto = new FarmResponseDto();
            dto.setFarmId(farm.getFarmId());
            dto.setFarmName(farm.getFarmName());
            dto.setLocation(farm.getLocation());
            dto.setTotalArea(farm.getTotalArea());
            
            if (farm.getFarmer() != null) {
                dto.setFarmerId(farm.getFarmer().getFarmerId());
            }
            
            return dto;
        }).collect(Collectors.toList());

        // Attach farms to the farmer response
        response.setFarms(farmDtos);
        
        return response;
    }
}