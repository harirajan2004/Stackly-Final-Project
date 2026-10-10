package com.example.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.constants.Constants;
import com.example.dto.ExpenseRequestDto;
import com.example.dto.ExpenseResponseDto;
import com.example.entity.Activity;
import com.example.entity.Expense;
import com.example.exception.ActivityNotFoundException;
import com.example.exception.ExpenseNotFoundException;
import com.example.mapper.ExpenseMapper;
import com.example.repository.ActivityRepository;
import com.example.repository.ExpenseRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private static final Logger logger =
            LoggerFactory.getLogger(ExpenseServiceImpl.class);

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ExpenseMapper expenseMapper;

    @Override
    @Transactional
    @CachePut(value = "expenses", key = "#result.expenseId")
    public ExpenseResponseDto createOrUpdateExpense(
            ExpenseRequestDto requestDto) {

        logger.info("Creating or updating expense");

        Expense expense;

        if (requestDto.getExpenseId() == null) {

            logger.info("Creating new expense");

            expense = expenseMapper.toEntity(requestDto);

        } else {

            logger.info(
                    "Updating expense with ID: {}",
                    requestDto.getExpenseId());

            expense = expenseRepository.findById(
                    requestDto.getExpenseId())
                    .orElseThrow(() ->
                            new ExpenseNotFoundException(
                                    Constants.EXPENSE_NOT_FOUND));

            expenseMapper.updateEntity(
                    requestDto,
                    expense);
        }

        Activity activity = activityRepository.findById(
                requestDto.getActivityId())
                .orElseThrow(() ->
                        new ActivityNotFoundException(
                                Constants.ACTIVITY_NOT_FOUND));

        expense.setActivity(activity);

        Expense savedExpense =
                expenseRepository.save(expense);

        logger.info(
                "Expense saved successfully with ID: {}",
                savedExpense.getExpenseId());

        return expenseMapper.toResponseDto(savedExpense);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "expenses", key = "#expenseId")
    public ExpenseResponseDto getExpenseById(
            Integer expenseId) {

        logger.info(
                "Fetching expense with ID: {}",
                expenseId);

        Expense expense =
                expenseRepository.findById(expenseId)
                        .orElseThrow(() ->
                                new ExpenseNotFoundException(
                                        Constants.EXPENSE_NOT_FOUND));

        logger.info(
                "Expense found with ID: {}",
                expenseId);

        return expenseMapper.toResponseDto(expense);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseResponseDto> getAllExpenses() {

        logger.info("Fetching all expenses");

        List<ExpenseResponseDto> expenses =
                expenseRepository.findAll()
                        .stream()
                        .map(expenseMapper::toResponseDto)
                        .collect(Collectors.toList());

        logger.info(
                "Total expenses fetched: {}",
                expenses.size());

        return expenses;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExpenseResponseDto> getExpensesByActivity(
            Integer activityId,
            int page,
            int size,
            String sortBy) {

        logger.info(
                "Fetching expenses for activity ID: {}, page: {}, size: {}, sortBy: {}",
                activityId,
                page,
                size,
                sortBy);

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(sortBy).ascending());

        Page<Expense> expenses =
                expenseRepository.findByActivityActivityId(
                        activityId,
                        pageable);

        logger.info(
                "Expenses fetched for activity ID: {}",
                activityId);

        return expenses.map(
                expenseMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpenseResponseDto> getExpensesByDate(
            LocalDate startDate,
            LocalDate endDate) {

        logger.info(
                "Fetching expenses between dates: {} and {}",
                startDate,
                endDate);

        List<ExpenseResponseDto> expenses =
                expenseRepository
                        .findByExpenseDateBetween(
                                startDate,
                                endDate)
                        .stream()
                        .map(expenseMapper::toResponseDto)
                        .collect(Collectors.toList());

        logger.info(
                "Total expenses fetched between dates: {}",
                expenses.size());

        return expenses;
    }

    @Override
    @Transactional(readOnly = true)
    public Double getTotalExpense() {

        logger.info("Calculating total expense");

        Double totalExpense =
                expenseRepository.getTotalExpense();

        logger.info(
                "Total expense: {}",
                totalExpense);

        return totalExpense;
    }

    @Override
    @Transactional(readOnly = true)
    public Double getTotalExpenseBetweenDates(
            LocalDate startDate,
            LocalDate endDate) {

        logger.info(
                "Calculating total expense between dates: {} and {}",
                startDate,
                endDate);

        Double totalExpense =
                expenseRepository.getTotalExpenseBetweenDates(
                        startDate,
                        endDate);

        logger.info(
                "Total expense between dates: {}",
                totalExpense);

        return totalExpense;
    }
   
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getExpenseReport() {

        logger.info("Generating expense report");

        List<Object[]> results =
                expenseRepository.getExpenseReport();

        List<Map<String, Object>> report =
                new ArrayList<>();

        for (Object[] row : results) {

            Map<String, Object> expenseDetails =
                    new LinkedHashMap<>();

            expenseDetails.put("expenseType", row[0]);
            expenseDetails.put("totalAmount", row[1]);
            expenseDetails.put("fieldId", row[2]);
            expenseDetails.put("fieldName", row[3]);
            expenseDetails.put("description", row[4]);

            report.add(expenseDetails);
        }

        logger.info(
                "Expense report generated with {} records",
                report.size());

        return report;
    }
   
   
    @Override
    @Transactional
    @CacheEvict(value = "expenses", key = "#expenseId")
    public void deleteExpense(Integer expenseId) {

        logger.info(
                "Deleting expense with ID: {}",
                expenseId);

        Expense expense =
                expenseRepository.findById(expenseId)
                        .orElseThrow(() ->
                                new ExpenseNotFoundException(
                                        Constants.EXPENSE_NOT_FOUND));

        expenseRepository.delete(expense);

        logger.info(
                "Expense deleted successfully with ID: {}",
                expenseId);
    }
}