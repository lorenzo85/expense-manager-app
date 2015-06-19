package org.cms.service.yard;

import org.cms.service.income.IncomeDto;
import org.cms.service.expense.ExpenseDto;

import java.util.ArrayList;
import java.util.List;

public class YardExtendedDto extends YardDto {

    private YardSummaryDto summary;
    private List<IncomeDto> incomes = new ArrayList<IncomeDto>();
    private List<ExpenseDto> expenses = new ArrayList<ExpenseDto>();

    public YardExtendedDto() {
    }

    public List<ExpenseDto> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<ExpenseDto> expenses) {
        this.expenses = expenses;
    }

    public void setIncomes(List<IncomeDto> incomes) {
        this.incomes = incomes;
    }

    public List<IncomeDto> getIncomes() {
        return incomes;
    }

    public void setSummary(YardSummaryDto summary) {
        this.summary = summary;
    }

    public YardSummaryDto getSummary() {
        return summary;
    }
}