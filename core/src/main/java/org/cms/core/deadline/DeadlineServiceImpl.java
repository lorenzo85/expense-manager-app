package org.cms.core.deadline;

import org.cms.core.expense.Expense;
import org.cms.core.expense.ExpenseRepository;
import org.dozer.Mapper;
import org.joda.money.CurrencyUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.cms.core.commons.PaymentState.UNPAID;

@Service
public class DeadlineServiceImpl implements DeadlineService {

    @Autowired
    protected Mapper mapper;
    @Autowired
    private CurrencyUnit currencyUnit;
    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public List<DeadlinesExpenseDto> listDeadlinesExpensesGroupedByCategory() {
        List<Expense> unpaidExpenses = expenseRepository
                .listByPaymentStateOrderedByYearAndMonthAndCategory(UNPAID);

        List<DeadlinesExpensesForCategoryTotals> unPaidMonthlyExpensesGroupedByCategory =
                new DeadlineExpenses(unpaidExpenses, currencyUnit)
                        .computeMonthlySumsForEachCategory();

        return unPaidMonthlyExpensesGroupedByCategory.stream()
                .map(expenseGroup -> mapper.map(expenseGroup, DeadlinesExpenseDto.class))
                .collect(toList());
    }
}
