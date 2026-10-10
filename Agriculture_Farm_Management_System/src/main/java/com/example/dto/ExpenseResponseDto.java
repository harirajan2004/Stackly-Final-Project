package com.example.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.enums.ExpenseType;

import lombok.Data;

@Data
public class ExpenseResponseDto {

    private Integer expenseId;

    private ExpenseType expenseType;

    private Double amount;

    private LocalDate expenseDate;

    private String description;

    private Integer activityId;
    
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime updatedDate;
    private String updatedBy;
}