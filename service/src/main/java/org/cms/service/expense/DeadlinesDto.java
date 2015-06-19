package org.cms.service.expense;

import org.joda.money.Money;

import java.util.List;
import java.util.Map;

public class DeadlinesDto {

    private String year;
    private String month;
    private Money total;
    private List<ExpenseDto> expenses;
    private Map<ExpenseCategory, Money> expensesSumsForCategory;

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

    public Money getTotal() {
        return total;
    }

    public void setTotal(Money total) {
        this.total = total;
    }

    public List<ExpenseDto> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<ExpenseDto> expenses) {
        this.expenses = expenses;
    }

    public Map<ExpenseCategory, Money> getExpensesSumsForCategory() {
        return expensesSumsForCategory;
    }

    public void setExpensesSumsForCategory(Map<ExpenseCategory, Money> expensesSumsForCategory) {
        this.expensesSumsForCategory = expensesSumsForCategory;
    }
}