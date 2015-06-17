package org.cms.data.dto;

import java.util.ArrayList;
import java.util.List;

public class ExtendedYardDto extends YardDto {

    private YardSummaryDto summary;
    private List<IncomeDto> incomes = new ArrayList<IncomeDto>();
    private List<ExpenseDto> expenses = new ArrayList<ExpenseDto>();


    public ExtendedYardDto() {
    }

    public ExtendedYardDto(YardDto dto) {
        super(dto);
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