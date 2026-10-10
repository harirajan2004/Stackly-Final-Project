package com.example.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.example.dto.ExpenseRequestDto;
import com.example.dto.ExpenseResponseDto;

public interface ExpenseService {

    ExpenseResponseDto createOrUpdateExpense(
            ExpenseRequestDto requestDto);

    ExpenseResponseDto getExpenseById(Integer expenseId);

    List<ExpenseResponseDto> getAllExpenses();

    Page<ExpenseResponseDto> getExpensesByActivity(
            Integer activityId, int page, int size, String sortBy);

    List<ExpenseResponseDto> getExpensesByDate(
            LocalDate startDate, LocalDate endDate);

    Double getTotalExpense();

    Double getTotalExpenseBetweenDates(
            LocalDate startDate, LocalDate endDate);

    List<Map<String, Object>> getExpenseReport();

    void deleteExpense(Integer expenseId);
}