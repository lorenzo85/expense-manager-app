package org.cms.core.deadline;

import org.apache.commons.lang3.tuple.Pair;
import org.cms.core.expense.Expense;
import org.joda.money.CurrencyUnit;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class DeadlineExpenses extends Deadline {

    private final CurrencyUnit currencyUnit;
    private final List<Expense> unpaidExpenses;

    public DeadlineExpenses(List<Expense> unpaidExpenses, CurrencyUnit currencyUnit) {
        this.unpaidExpenses = unpaidExpenses;
        this.currencyUnit = currencyUnit;
    }

    public List<DeadlinesExpensesForCategoryTotals> computeMonthlySumsForEachCategory() {
        Map<Pair<String, String>, List<Expense>> expensesByYearAndMonth = groupByYearAndMonth(unpaidExpenses);
        return expensesByYearAndMonth
                .entrySet()
                .stream()
                .map(this::getDeadlinesExpensesForCategoryTotals)
                .collect(toList());
    }

    private DeadlinesExpensesForCategoryTotals getDeadlinesExpensesForCategoryTotals(Map.Entry<Pair<String, String>, List<Expense>> mapEntry) {
        Pair<String, String> key = mapEntry.getKey();
        List<Expense> expenses = mapEntry.getValue();
        String year = key.getLeft();
        String month = key.getRight();
        DeadlinesExpensesForCategoryTotals deadlines = new DeadlinesExpensesForCategoryTotals(year, month, currencyUnit, expenses);
        deadlines.computeSumsForEachExpenseCategory();
        return deadlines;
    }
}
