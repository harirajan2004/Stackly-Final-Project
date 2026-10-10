
package com.example.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.HarvestRequestDto;
import com.example.dto.HarvestResponseDto;
import com.example.service.HarvestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Harvest", description = "Harvest management APIs")
public class HarvestController {

    @Autowired
    private HarvestService harvestService;

    @Operation(
            summary = "Create or update harvest",
            description = "Creates a new harvest record or updates an existing harvest")
    @ApiResponse(
            responseCode = "200",
            description = "Harvest saved successfully")
    @PostMapping("/api/harvest")
    public ResponseEntity<HarvestResponseDto> createOrUpdateHarvest(
            @Valid @RequestBody HarvestRequestDto requestDto) {

        HarvestResponseDto response =
                harvestService.createOrUpdateHarvest(requestDto);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get harvest by ID",
            description = "Returns a harvest using harvest ID")
    @ApiResponse(
            responseCode = "200",
            description = "Harvest found successfully")
    @GetMapping("/api/harvest/{harvestId}")
    public ResponseEntity<HarvestResponseDto> getHarvestById(
            @PathVariable Integer harvestId) {

        HarvestResponseDto response =
                harvestService.getHarvestById(harvestId);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get all harvests",
            description = "Returns all harvest records")
    @ApiResponse(
            responseCode = "200",
            description = "Harvests retrieved successfully")
    @GetMapping("/api/harvest")
    public ResponseEntity<List<HarvestResponseDto>> getAllHarvests() {

        List<HarvestResponseDto> response =
                harvestService.getAllHarvests();

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get harvests by crop",
            description = "Returns harvest records belonging to a crop with pagination and sorting")
    @ApiResponse(
            responseCode = "200",
            description = "Harvests retrieved successfully")
    @GetMapping("/api/harvest/crop/{cropId}")
    public ResponseEntity<Page<HarvestResponseDto>> getHarvestsByCrop(
            @PathVariable Integer cropId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "harvestDate") String sortBy) {

        Page<HarvestResponseDto> harvests =
                harvestService.getHarvestsByCrop(
                        cropId,
                        page,
                        size,
                        sortBy);

        return ResponseEntity.ok()
                .header("X-Total-Harvests",
                        String.valueOf(harvests.getTotalElements()))
                .body(harvests);
    }

    @Operation(
            summary = "Get harvests by date",
            description = "Returns harvest records between the given dates")
    @ApiResponse(
            responseCode = "200",
            description = "Harvests retrieved successfully")
    @GetMapping("/api/harvest/date")
    public ResponseEntity<List<HarvestResponseDto>> getHarvestsByDate(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        List<HarvestResponseDto> response =
                harvestService.getHarvestsByDate(
                        startDate,
                        endDate);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get total harvest quantity",
            description = "Returns the total quantity of all harvested crops")
    @ApiResponse(
            responseCode = "200",
            description = "Total harvest quantity retrieved successfully")
    @GetMapping("/api/harvest/total")
    public ResponseEntity<Double> getTotalHarvestQuantity() {

        Double totalQuantity =
                harvestService.getTotalHarvestQuantity();

        return new ResponseEntity<>(
                totalQuantity,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get total harvest quantity by date",
            description = "Returns total harvest quantity between two dates")
    @ApiResponse(
            responseCode = "200",
            description = "Total harvest quantity retrieved successfully")
    @GetMapping("/api/harvest/total/date")
    public ResponseEntity<Double> getTotalHarvestQuantityBetweenDates(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        Double totalQuantity =
                harvestService.getTotalHarvestQuantityBetweenDates(
                        startDate,
                        endDate);

        return new ResponseEntity<>(
                totalQuantity,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get detailed harvest report",
            description = "Returns harvest details including crop name, date, quantity, unit, and selling price")
    @ApiResponse(
            responseCode = "200",
            description = "Harvest report retrieved successfully")
    @GetMapping("/api/harvest/report")
    public ResponseEntity<List<Map<String, Object>>> getHarvestReport() {

        List<Map<String, Object>> report =
                harvestService.getHarvestReport();

        return new ResponseEntity<>(
                report,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Delete harvest",
            description = "Deletes a harvest using harvest ID")
    @ApiResponse(
            responseCode = "200",
            description = "Harvest deleted successfully")
    @DeleteMapping("/api/harvest/{harvestId}")
    public ResponseEntity<String> deleteHarvest(
            @PathVariable Integer harvestId) {

        harvestService.deleteHarvest(harvestId);

        return new ResponseEntity<>(
                "Harvest deleted successfully",
                HttpStatus.OK);
    }
}
