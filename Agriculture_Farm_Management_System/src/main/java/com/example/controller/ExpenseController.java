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

import com.example.dto.ExpenseRequestDto;
import com.example.dto.ExpenseResponseDto;
import com.example.service.ExpenseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Expense", description = "Expense management APIs")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Operation(
            summary = "Create or update expense",
            description = "Creates a new expense or updates an existing expense")
    @ApiResponse(
            responseCode = "200",
            description = "Expense saved successfully")
    @PostMapping("/api/expense")
    public ResponseEntity<ExpenseResponseDto> createOrUpdateExpense(
            @Valid @RequestBody ExpenseRequestDto requestDto) {

        ExpenseResponseDto response =
                expenseService.createOrUpdateExpense(requestDto);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get expense by ID",
            description = "Returns an expense using expense ID")
    @ApiResponse(
            responseCode = "200",
            description = "Expense found successfully")
    @GetMapping("/api/expense/{expenseId}")
    public ResponseEntity<ExpenseResponseDto> getExpenseById(
            @PathVariable Integer expenseId) {

        ExpenseResponseDto response =
                expenseService.getExpenseById(expenseId);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get all expenses",
            description = "Returns all expenses")
    @ApiResponse(
            responseCode = "200",
            description = "Expenses retrieved successfully")
    @GetMapping("/api/expense")
    public ResponseEntity<List<ExpenseResponseDto>> getAllExpenses() {

        List<ExpenseResponseDto> response =
                expenseService.getAllExpenses();

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get expenses by activity",
            description = "Returns expenses belonging to an activity with pagination and sorting")
    @ApiResponse(
            responseCode = "200",
            description = "Expenses retrieved successfully")
    @GetMapping("/api/expense/activity/{activityId}")
    public ResponseEntity<Page<ExpenseResponseDto>> getExpensesByActivity(
            @PathVariable Integer activityId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "expenseDate") String sortBy) {

        Page<ExpenseResponseDto> expenses =
                expenseService.getExpensesByActivity(
                        activityId,
                        page,
                        size,
                        sortBy);

        return ResponseEntity.ok()
                .header("X-Total-Expenses",
                        String.valueOf(expenses.getTotalElements()))
                .body(expenses);
    }

    @Operation(
            summary = "Get expenses by date",
            description = "Returns expenses between the given dates")
    @ApiResponse(
            responseCode = "200",
            description = "Expenses retrieved successfully")
    @GetMapping("/api/expense/date")
    public ResponseEntity<List<ExpenseResponseDto>> getExpensesByDate(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        List<ExpenseResponseDto> response =
                expenseService.getExpensesByDate(
                        startDate,
                        endDate);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get total expense",
            description = "Returns the total expense amount")
    @ApiResponse(
            responseCode = "200",
            description = "Total expense retrieved successfully")
    @GetMapping("/api/expense/total")
    public ResponseEntity<Double> getTotalExpense() {

        Double totalExpense =
                expenseService.getTotalExpense();

        return new ResponseEntity<>(
                totalExpense,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get total expense by date",
            description = "Returns the total expense amount between two dates")
    @ApiResponse(
            responseCode = "200",
            description = "Total expense retrieved successfully")
    @GetMapping("/api/expense/total/date")
    public ResponseEntity<Double> getTotalExpenseBetweenDates(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {

        Double totalExpense =
                expenseService.getTotalExpenseBetweenDates(
                        startDate,
                        endDate);

        return new ResponseEntity<>(
                totalExpense,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Get detailed expense report",
            description = "Returns total expense grouped by expense type, field, and description")
    @ApiResponse(
            responseCode = "200",
            description = "Expense report retrieved successfully")
    @GetMapping("/api/expense/report")
    public ResponseEntity<List<Map<String, Object>>> getExpenseReport() {

        List<Map<String, Object>> report =
                expenseService.getExpenseReport();

        return new ResponseEntity<>(
                report,
                HttpStatus.OK);
    }

    @Operation(
            summary = "Delete expense",
            description = "Deletes an expense using expense ID")
    @ApiResponse(
            responseCode = "200",
            description = "Expense deleted successfully")
    @DeleteMapping("/api/expense/{expenseId}")
    public ResponseEntity<String> deleteExpense(
            @PathVariable Integer expenseId) {

        expenseService.deleteExpense(expenseId);

        return new ResponseEntity<>(
                "Expense deleted successfully",
                HttpStatus.OK);
    }
}