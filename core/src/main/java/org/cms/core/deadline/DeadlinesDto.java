package org.cms.core.deadline;

import lombok.Getter;
import lombok.Setter;
import org.cms.core.expense.ExpenseCategory;
import org.cms.core.expense.ExpenseDto;
import org.joda.money.Money;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class DeadlinesDto {

    private String year;
    private String month;
    private Money total;
    private List<ExpenseDto> expenses;
    private Map<ExpenseCategory, Money> expensesSumsForCategory;

}