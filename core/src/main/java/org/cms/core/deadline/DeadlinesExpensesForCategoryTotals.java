package org.cms.core.deadline;

import org.cms.core.expense.Expense;
import org.cms.core.expense.PaymentCategory;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.joda.money.Money.of;

public class DeadlinesExpensesForCategoryTotals {

    String year;
    String month;
    Money total;
    List<Expense> expenses;
    Map<PaymentCategory, Money> expensesSumsForCategory = new HashMap<>();

    public DeadlinesExpensesForCategoryTotals(String year, String month, CurrencyUnit currencyUnit, List<Expense> expenses) {
        this.year = year;
        this.month = month;
        this.expenses = expenses;
        this.total = of(currencyUnit, 0);
    }

    public void computeSumsForEachExpenseCategory() {
        expenses.forEach(payment -> {
            Money amount = payment.getAmount();
            total = total.plus(amount);
            expensesSumsForCategory.compute(payment.getCategory(),
                    (category, partialSum) -> (partialSum == null) ? amount : partialSum.plus(amount));
        });
    }
}
