package com.example.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import com.example.dto.HarvestRequestDto;
import com.example.dto.HarvestResponseDto;
import com.example.entity.Crop;
import com.example.entity.Harvest;
import com.example.exception.CropNotFoundException;
import com.example.exception.HarvestNotFoundException;
import com.example.mapper.HarvestMapper;
import com.example.repository.CropRepository;
import com.example.repository.HarvestRepository;

@Service
public class HarvestServiceImpl implements HarvestService {

    private static final Logger logger =
            LoggerFactory.getLogger(HarvestServiceImpl.class);

    @Autowired
    private HarvestRepository harvestRepository;

    @Autowired
    private CropRepository cropRepository;

    @Autowired
    private HarvestMapper harvestMapper;

    @Override
    @Transactional
    @CachePut(value = "harvests", key = "#result.harvestId")
    public HarvestResponseDto createOrUpdateHarvest(
            HarvestRequestDto requestDto) {

        logger.info("Creating or updating harvest");

        Harvest harvest;

        if (requestDto.getHarvestId() == null) {

            logger.info("Creating new harvest");

            harvest = harvestMapper.toEntity(requestDto);

        } else {

            logger.info(
                    "Updating harvest with ID: {}",
                    requestDto.getHarvestId());

            harvest = harvestRepository.findById(
                    requestDto.getHarvestId())
                    .orElseThrow(() ->
                            new HarvestNotFoundException(
                                    Constants.HARVEST_NOT_FOUND));

            harvestMapper.updateEntity(
                    requestDto,
                    harvest);
        }

        Crop crop = cropRepository.findById(
                requestDto.getCropId())
                .orElseThrow(() ->
                        new CropNotFoundException(
                                Constants.CROP_NOT_FOUND));

        harvest.setCrop(crop);

        Harvest savedHarvest =
                harvestRepository.save(harvest);

        logger.info(
                "Harvest saved successfully with ID: {}",
                savedHarvest.getHarvestId());

        return harvestMapper.toResponseDto(savedHarvest);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "harvests", key = "#harvestId")
    public HarvestResponseDto getHarvestById(
            Integer harvestId) {

        logger.info(
                "Fetching harvest with ID: {}",
                harvestId);

        Harvest harvest =
                harvestRepository.findById(harvestId)
                        .orElseThrow(() ->
                                new HarvestNotFoundException(
                                        Constants.HARVEST_NOT_FOUND));

        logger.info(
                "Harvest found with ID: {}",
                harvestId);

        return harvestMapper.toResponseDto(harvest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HarvestResponseDto> getAllHarvests() {

        logger.info("Fetching all harvests");

        List<HarvestResponseDto> harvests =
                harvestRepository.findAll()
                        .stream()
                        .map(harvestMapper::toResponseDto)
                        .collect(Collectors.toList());

        logger.info(
                "Total harvests fetched: {}",
                harvests.size());

        return harvests;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HarvestResponseDto> getHarvestsByCrop(
            Integer cropId,
            int page,
            int size,
            String sortBy) {

        logger.info(
                "Fetching harvests for crop ID: {}, page: {}, size: {}, sortBy: {}",
                cropId,
                page,
                size,
                sortBy);

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(sortBy).ascending());

        Page<Harvest> harvests =
                harvestRepository.findByCropCropId(
                        cropId,
                        pageable);

        logger.info(
                "Harvests fetched for crop ID: {}",
                cropId);

        return harvests.map(
                harvestMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HarvestResponseDto> getHarvestsByDate(
            LocalDate startDate,
            LocalDate endDate) {

        logger.info(
                "Fetching harvests between dates: {} and {}",
                startDate,
                endDate);

        List<HarvestResponseDto> harvests =
                harvestRepository
                        .findByHarvestDateBetween(
                                startDate,
                                endDate)
                        .stream()
                        .map(harvestMapper::toResponseDto)
                        .collect(Collectors.toList());

        logger.info(
                "Total harvests fetched between dates: {}",
                harvests.size());

        return harvests;
    }

    @Override
    @Transactional(readOnly = true)
    public Double getTotalHarvestQuantity() {

        logger.info(
                "Calculating total harvest quantity");

        Double totalQuantity =
                harvestRepository.getTotalHarvestQuantity();

        logger.info(
                "Total harvest quantity: {}",
                totalQuantity);

        return totalQuantity;
    }

    @Override
    @Transactional(readOnly = true)
    public Double getTotalHarvestQuantityBetweenDates(
            LocalDate startDate,
            LocalDate endDate) {

        logger.info(
                "Calculating total harvest quantity between dates: {} and {}",
                startDate,
                endDate);

        Double totalQuantity =
                harvestRepository
                        .getTotalHarvestQuantityBetweenDates(
                                startDate,
                                endDate);

        logger.info(
                "Total harvest quantity between dates: {}",
                totalQuantity);

        return totalQuantity;
    }


@Override
@Transactional(readOnly = true)
public List<Map<String, Object>> getHarvestReport() {

    logger.info("Generating detailed harvest report");

    List<Object[]> results =
            harvestRepository.getDetailedHarvestReport();

    List<Map<String, Object>> report = new ArrayList<>();

    for (Object[] row : results) {

        Map<String, Object> record = new LinkedHashMap<>();

        record.put("harvestId", row[0]);
        record.put("cropName", row[1]);
        record.put("harvestDate", row[2]);
        record.put("quantity", row[3]);
        record.put("unit", row[4]);
        record.put("sellingPrice", row[5]);

        report.add(record);
    }

    logger.info(
            "Detailed harvest report generated with {} records",
            report.size());

    return report;
}


    @Override
    @Transactional
    @CacheEvict(value = "harvests", key = "#harvestId")
    public void deleteHarvest(Integer harvestId) {

        logger.info(
                "Deleting harvest with ID: {}",
                harvestId);

        Harvest harvest =
                harvestRepository.findById(harvestId)
                        .orElseThrow(() ->
                                new HarvestNotFoundException(
                                        Constants.HARVEST_NOT_FOUND));

        harvestRepository.delete(harvest);

        logger.info(
                "Harvest deleted successfully with ID: {}",
                harvestId);
    }
}