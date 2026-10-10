package com.example.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Harvest;

@Repository
public interface HarvestRepository extends JpaRepository<Harvest, Integer> {

    Page<Harvest> findByCropCropId(
            Integer cropId, Pageable pageable);

    @Query("""
            SELECT h FROM Harvest h
            WHERE h.harvestDate BETWEEN :startDate AND :endDate
            """)
    List<Harvest> findByHarvestDateBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT COALESCE(SUM(h.quantity), 0) FROM Harvest h")
    Double getTotalHarvestQuantity();

    @Query("""
            SELECT COALESCE(SUM(h.quantity), 0)
            FROM Harvest h
            WHERE h.harvestDate BETWEEN :startDate AND :endDate
            """)
    Double getTotalHarvestQuantityBetweenDates(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);


@Query("""
        SELECT h.harvestId,
               h.crop.cropName,
               h.harvestDate,
               h.quantity,
               h.unit,
               h.sellingPrice
        FROM Harvest h
        ORDER BY h.harvestDate DESC
        """)
List<Object[]> getDetailedHarvestReport();

}