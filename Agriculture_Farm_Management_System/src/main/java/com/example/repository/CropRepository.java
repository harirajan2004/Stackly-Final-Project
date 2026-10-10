package com.example.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Crop;
import com.example.enums.CropStatus;

@Repository
public interface CropRepository extends JpaRepository<Crop, Integer> {

    Page<Crop> findByStatus(CropStatus status, Pageable pageable);

    @Query("SELECT c FROM Crop c WHERE c.field.fieldId = :fieldId")
    List<Crop> findByFieldId(@Param("fieldId") Integer fieldId);

    @Query("""
            SELECT c FROM Crop c
            WHERE c.plantingDate BETWEEN :startDate AND :endDate
            """)
    List<Crop> findByPlantingDateBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
    
    
    @Query("""
            SELECT c FROM Crop c
            WHERE c.expectedHarvestDate <= :today
            """)
    List<Crop> findByExpectedHarvestDateBeforeOrEqual(
            @Param("today") LocalDate today);
}