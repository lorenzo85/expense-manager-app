package com.spring.cms.service.dto;

import com.spring.cms.persistence.domain.ExpenseCategory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.math.RoundingMode.HALF_UP;

public class DeadlinesDto {

    private String year;
    private String month;
    private BigDecimal total = getZeroAmount();
    private List<ExpenseDto> expenses = new ArrayList<>();
    private Map<ExpenseCategory, BigDecimal> EXPENSES_CATEGORY_TOTALS = new HashMap<>();

    public DeadlinesDto() {
        for(ExpenseCategory category : ExpenseCategory.values()) {
            EXPENSES_CATEGORY_TOTALS.put(category, getZeroAmount());
        }
    }

    public DeadlinesDto(String year, String month) {
        this();
        this.year = year;
        this.month = month;
    }

    public void addExpense(ExpenseDto expense) {
        BigDecimal amount = expense.getAmount();
        total = total.add(amount);

        ExpenseCategory category = expense.getCategory();
        BigDecimal updated = EXPENSES_CATEGORY_TOTALS.get(category).add(amount);
        EXPENSES_CATEGORY_TOTALS.put(category, updated);

        expenses.add(expense);
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<ExpenseDto> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<ExpenseDto> expenses) {
        this.expenses = expenses;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Map<ExpenseCategory, BigDecimal> getExpensesCategoryTotals() {
        return EXPENSES_CATEGORY_TOTALS;
    }

    public void computeTotal() {
        for(ExpenseDto dto : expenses) {
            total = total.add(dto.getAmount());
        }
    }

    private static BigDecimal getZeroAmount() {
        return new BigDecimal(0.00).setScale(2, HALF_UP);
    }
}