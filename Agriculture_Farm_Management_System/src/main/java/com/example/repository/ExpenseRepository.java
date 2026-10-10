
package com.example.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    Page<Expense> findByActivityActivityId(
            Integer activityId, Pageable pageable);

    @Query("""
            SELECT e FROM Expense e
            WHERE e.expenseDate BETWEEN :startDate AND :endDate
            """)
    List<Expense> findByExpenseDateBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e")
    Double getTotalExpense();

    @Query("""
            SELECT COALESCE(SUM(e.amount), 0)
            FROM Expense e
            WHERE e.expenseDate BETWEEN :startDate AND :endDate
            """)
    Double getTotalExpenseBetweenDates(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("""
            SELECT e.expenseType,
                   SUM(e.amount),
                   f.fieldId,
                   f.fieldName,
                   e.description
            FROM Expense e
            LEFT JOIN e.activity a
            LEFT JOIN a.field f
            GROUP BY e.expenseType,
                     f.fieldId,
                     f.fieldName,
                     e.description
            ORDER BY e.expenseType
            """)
    List<Object[]> getExpenseReport();
   
}

