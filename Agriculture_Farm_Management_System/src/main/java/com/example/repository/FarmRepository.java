package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Farm;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Integer> {

    Page<Farm> findByFarmerFarmerId(Integer farmerId, Pageable pageable);
    
    @Query("SELECT f FROM Farm f WHERE f.farmer.farmerId = :farmerId")
    List<Farm> findByFarmerId(@Param("farmerId") Integer farmerId);
}
