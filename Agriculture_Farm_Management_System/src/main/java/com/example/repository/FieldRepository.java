package com.example.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Field;

@Repository
public interface FieldRepository extends JpaRepository<Field, Integer> {

    Page<Field> findByFarmFarmId(Integer farmId, Pageable pageable);
}