package org.cms.core.deadline;

import lombok.Getter;
import lombok.Setter;
import org.cms.core.expense.Expense;
import org.cms.core.expense.PaymentCategory;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.joda.money.Money.of;

@Getter
@Setter
public class DeadlinesExpensesForCategoryTotals {

    String year;
    String month;
    Money total;
    List<Expense> expenses = new ArrayList<>();
    Map<PaymentCategory, Money> expensesSumsForCategory = new HashMap<>();

    public DeadlinesExpensesForCategoryTotals(String year, String month, CurrencyUnit currencyUnit) {
        this.year = year;
        this.month = month;
        this.total = of(currencyUnit, 0);
    }

    public void addToTotal(Money amount) {
        total = total.plus(amount);
    }

    public void addToTotalForCategory(Money amount, PaymentCategory category) {
        expensesSumsForCategory.compute(category,
                (cat, partialSum) -> (partialSum == null) ? amount : partialSum.plus(amount));
    }
}
