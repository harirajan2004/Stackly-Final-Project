package com.example.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {

    Page<Activity> findByFieldFieldId(Integer fieldId, Pageable pageable);

    @Query("""
            SELECT a FROM Activity a
            WHERE a.activityDate BETWEEN :startDate AND :endDate
            """)
    List<Activity> findByActivityDateBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}