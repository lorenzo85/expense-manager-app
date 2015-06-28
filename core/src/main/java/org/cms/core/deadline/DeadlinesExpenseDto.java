package org.cms.core.deadline;

import lombok.Getter;
import lombok.Setter;
import org.cms.core.expense.PaymentCategory;
import org.cms.core.expense.ExpenseDto;
import org.joda.money.Money;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class DeadlinesExpenseDto {

    private String year;
    private String month;
    private Money total;
    private List<ExpenseDto> expenses;
    private Map<PaymentCategory, Money> expensesSumsForCategory;

}