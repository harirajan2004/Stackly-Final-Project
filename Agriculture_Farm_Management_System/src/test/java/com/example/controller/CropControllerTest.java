
package com.example.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.service.CropService;

@WebMvcTest(controllers = CropController.class)
class CropControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CropService cropService;

    @MockitoBean
    private CacheManager cacheManager;

    @Test
    void testGetAllCrops() throws Exception {
        when(cropService.getAllCrops()).thenReturn(List.of());

        mockMvc.perform(get("/api/crop"))
               .andExpect(status().isOk());
    }
}