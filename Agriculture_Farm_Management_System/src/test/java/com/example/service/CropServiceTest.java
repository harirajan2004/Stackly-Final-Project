package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.dto.CropResponseDto;
import com.example.entity.Crop;
import com.example.mapper.CropMapper;
import com.example.repository.CropRepository;

@ExtendWith(MockitoExtension.class)
class CropServiceTest {

    @Mock
    private CropRepository cropRepository;

    @InjectMocks
    private CropServiceImpl cropService;
    
    @Mock
    private CropMapper cropMapper;
    
    @Test
    void testGetAllCrops() {
        when(cropRepository.findAll())
                .thenReturn(List.of(new Crop()));

        List<CropResponseDto> crops =
                cropService.getAllCrops();

        assertEquals(1, crops.size());
    }
}