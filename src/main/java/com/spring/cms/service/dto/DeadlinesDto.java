package com.spring.cms.service.dto;

import com.spring.cms.persistence.domain.ExpenseCategory;
import org.joda.money.Money;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeadlinesDto {

    private String year;
    private String month;
    private Money total;
    private List<ExpenseDto> expenses = new ArrayList<>();
    private Map<ExpenseCategory, Money> expenseCategoryTotals = new HashMap<>();

    public DeadlinesDto(String year, String month) {
        this.year = year;
        this.month = month;
    }

    public void addExpense(ExpenseDto expense) {
        this.expenses.add(expense);
    }

    public Money getTotalForCategory(ExpenseCategory category) {
        return expenseCategoryTotals.get(category);
    }

    public List<ExpenseDto> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<ExpenseDto> expenses) {
        this.expenses = expenses;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public Money getTotal() {
        return total;
    }

    public Map<ExpenseCategory, Money> getExpenseCategoryTotals() {
        return expenseCategoryTotals;
    }

    public void updateTotal(Money total) {
        this.total = total;
    }

    public void updateTotalForCategory(ExpenseCategory category, Money monthCategoryTotal) {
        this.expenseCategoryTotals.put(category, monthCategoryTotal);
    }
}