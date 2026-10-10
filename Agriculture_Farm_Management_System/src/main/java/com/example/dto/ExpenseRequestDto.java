package com.example.dto;

import java.time.LocalDate;

import com.example.enums.ExpenseType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ExpenseRequestDto {

    private Integer expenseId;

    @NotNull(message = "Expense type is required")
    private ExpenseType expenseType;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private Double amount;

    @NotNull(message = "Expense date is required")
    private LocalDate expenseDate;

    private String description;

    @NotNull(message = "Activity ID is required")
    private Integer activityId;
}