package com.example.service;

import java.time.LocalDate;
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
import com.example.dto.CropRequestDto;
import com.example.dto.CropResponseDto;
import com.example.entity.Crop;
import com.example.entity.Field;
import com.example.enums.CropStatus;
import com.example.exception.CropNotFoundException;
import com.example.exception.FieldNotFoundException;
import com.example.mapper.CropMapper;
import com.example.repository.CropRepository;
import com.example.repository.FieldRepository;

@Service
public class CropServiceImpl implements CropService {

    private static final Logger logger =
            LoggerFactory.getLogger(CropServiceImpl.class);

    @Autowired
    private CropRepository cropRepository;

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private CropMapper cropMapper;

    @Override
    @Transactional
    @CachePut(value = "crops", key = "#result.cropId")
    public CropResponseDto createOrUpdateCrop(
            CropRequestDto requestDto) {

        logger.info("Creating or updating crop");

        Crop crop;

        if (requestDto.getCropId() == null) {

            logger.info("Creating new crop");

            crop = cropMapper.toEntity(requestDto);

        } else {

            logger.info(
                    "Updating crop with ID: {}",
                    requestDto.getCropId());

            crop = cropRepository.findById(
                    requestDto.getCropId())
                    .orElseThrow(() ->
                            new CropNotFoundException(
                                    Constants.CROP_NOT_FOUND));

            cropMapper.updateEntity(
                    requestDto,
                    crop);
        }

        Field field = fieldRepository.findById(
                requestDto.getFieldId())
                .orElseThrow(() ->
                        new FieldNotFoundException(
                                Constants.FIELD_NOT_FOUND));

        crop.setField(field);

        Crop savedCrop =
                cropRepository.save(crop);

        logger.info(
                "Crop saved successfully with ID: {}",
                savedCrop.getCropId());

        return cropMapper.toResponseDto(savedCrop);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "crops", key = "#cropId")
    public CropResponseDto getCropById(
            Integer cropId) {

        logger.info(
                "Fetching crop with ID: {}",
                cropId);

        Crop crop = cropRepository.findById(cropId)
                .orElseThrow(() ->
                        new CropNotFoundException(
                                Constants.CROP_NOT_FOUND));

        logger.info(
                "Crop found with ID: {}",
                cropId);

        return cropMapper.toResponseDto(crop);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CropResponseDto> getAllCrops() {

        logger.info("Fetching all crops");

        List<CropResponseDto> crops =
                cropRepository.findAll()
                        .stream()
                        .map(cropMapper::toResponseDto)
                        .collect(Collectors.toList());

        logger.info(
                "Total crops fetched: {}",
                crops.size());

        return crops;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CropResponseDto> getCropsByStatus(
            CropStatus status,
            int page,
            int size,
            String sortBy) {

        logger.info(
                "Fetching crops by status: {}, page: {}, size: {}, sortBy: {}",
                status,
                page,
                size,
                sortBy);

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(sortBy).ascending());

        Page<Crop> crops =
                cropRepository.findByStatus(
                        status,
                        pageable);

        logger.info(
                "Crops fetched for status: {}",
                status);

        return crops.map(cropMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CropResponseDto> getCropsByField(
            Integer fieldId) {

        logger.info(
                "Fetching crops for field ID: {}",
                fieldId);

        List<CropResponseDto> crops =
                cropRepository.findByFieldId(fieldId)
                        .stream()
                        .map(cropMapper::toResponseDto)
                        .collect(Collectors.toList());

        logger.info(
                "Total crops fetched for field ID {}: {}",
                fieldId,
                crops.size());

        return crops;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CropResponseDto> getCropsByPlantingDate(
            LocalDate startDate,
            LocalDate endDate) {

        logger.info(
                "Fetching crops between planting dates: {} and {}",
                startDate,
                endDate);

        List<CropResponseDto> crops =
                cropRepository
                        .findByPlantingDateBetween(
                                startDate,
                                endDate)
                        .stream()
                        .map(cropMapper::toResponseDto)
                        .collect(Collectors.toList());

        logger.info(
                "Total crops fetched between dates: {}",
                crops.size());

        return crops;
    }

    @Override
    @Transactional
    @CacheEvict(value = "crops", key = "#cropId")
    public void deleteCrop(Integer cropId) {

        logger.info(
                "Deleting crop with ID: {}",
                cropId);

        Crop crop = cropRepository.findById(cropId)
                .orElseThrow(() ->
                        new CropNotFoundException(
                                Constants.CROP_NOT_FOUND));

        cropRepository.delete(crop);

        logger.info(
                "Crop deleted successfully with ID: {}",
                cropId);
    }
}