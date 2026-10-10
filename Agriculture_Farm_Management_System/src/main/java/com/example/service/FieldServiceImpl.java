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
import com.example.dto.FieldRequestDto;
import com.example.dto.FieldResponseDto;
import com.example.entity.Field;
import com.example.entity.Farm;
import com.example.exception.FarmNotFoundException;
import com.example.exception.FieldNotFoundException;
import com.example.mapper.FieldMapper;
import com.example.repository.FarmRepository;
import com.example.repository.FieldRepository;

@Service
public class FieldServiceImpl implements FieldService {

    private static final Logger logger =
            LoggerFactory.getLogger(FieldServiceImpl.class);

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private FieldMapper fieldMapper;

    @Override
    @Transactional
    @CachePut(value = "fields", key = "#result.fieldId")
    public FieldResponseDto createOrUpdateField(
            FieldRequestDto requestDto) {

        logger.info("Creating or updating field");

        Field field;

        if (requestDto.getFieldId() == null) {

            logger.info("Creating new field");

            field = fieldMapper.toEntity(requestDto);

        } else {

            logger.info(
                    "Updating field with ID: {}",
                    requestDto.getFieldId());

            field = fieldRepository.findById(
                    requestDto.getFieldId())
                    .orElseThrow(() ->
                            new FieldNotFoundException(
                                    Constants.FIELD_NOT_FOUND));

            fieldMapper.updateEntity(
                    requestDto,
                    field);
        }

        Farm farm = farmRepository.findById(
                requestDto.getFarmId())
                .orElseThrow(() ->
                        new FarmNotFoundException(
                                Constants.FARM_NOT_FOUND));

        field.setFarm(farm);

        Field savedField =
                fieldRepository.save(field);

        logger.info(
                "Field saved successfully with ID: {}",
                savedField.getFieldId());

        return fieldMapper.toResponseDto(savedField);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "fields", key = "#fieldId")
    public FieldResponseDto getFieldById(
            Integer fieldId) {

        logger.info(
                "Fetching field with ID: {}",
                fieldId);

        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() ->
                        new FieldNotFoundException(
                                Constants.FIELD_NOT_FOUND));

        logger.info(
                "Field found with ID: {}",
                fieldId);

        return fieldMapper.toResponseDto(field);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FieldResponseDto> getAllFields() {

        logger.info("Fetching all fields");

        List<FieldResponseDto> fields =
                fieldRepository.findAll()
                        .stream()
                        .map(fieldMapper::toResponseDto)
                        .collect(Collectors.toList());

        logger.info(
                "Total fields fetched: {}",
                fields.size());

        return fields;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FieldResponseDto> getFieldsByFarm(
            Integer farmId,
            int page,
            int size,
            String sortBy) {

        logger.info(
                "Fetching fields for farm ID: {}, page: {}, size: {}, sortBy: {}",
                farmId,
                page,
                size,
                sortBy);

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(sortBy).ascending());

        Page<Field> fields =
                fieldRepository.findByFarmFarmId(
                        farmId,
                        pageable);

        logger.info(
                "Fields fetched for farm ID: {}",
                farmId);

        return fields.map(fieldMapper::toResponseDto);
    }

    @Override
    @Transactional
    @CacheEvict(value = "fields", key = "#fieldId")
    public void deleteField(Integer fieldId) {

        logger.info(
                "Deleting field with ID: {}",
                fieldId);

        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() ->
                        new FieldNotFoundException(
                                Constants.FIELD_NOT_FOUND));

        fieldRepository.delete(field);

        logger.info(
                "Field deleted successfully with ID: {}",
                fieldId);
    }
}