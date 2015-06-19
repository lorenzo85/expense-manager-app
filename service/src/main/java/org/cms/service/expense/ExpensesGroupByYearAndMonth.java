package org.cms.service.expense;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.joda.money.Money.of;

public class ExpensesGroupByYearAndMonth {

    String year;
    String month;
    Money total;
    List<Expense> expenses;
    Map<ExpenseCategory, Money> expensesSumsForCategory = new HashMap<>();

    private ExpensesGroupByYearAndMonth(Builder builder) {
        this.year = builder.year;
        this.month = builder.month;
        this.expenses = builder.expenses;
        this.total = of(builder.currencyUnit, 0);
        computeSumsForEachExpenseCategory();
    }

    private void computeSumsForEachExpenseCategory() {
        expenses.forEach(expense -> {
            Money amount = expense.getAmount();
            total = total.plus(amount);
            expensesSumsForCategory.compute(expense.category,
                    (category, partialSum) -> (partialSum == null) ? amount : partialSum.plus(amount));
        });
    }

    public static Builder builder(CurrencyUnit currencyUnit) {
        return new Builder(currencyUnit);
    }

    public static class Builder {
        private String year;
        private String month;
        private List<Expense> expenses;
        private CurrencyUnit currencyUnit;

        private Builder(CurrencyUnit currencyUnit) {
            this.currencyUnit = currencyUnit;
        }

        public Builder year(String year) {
            this.year = year;
            return this;
        }

        public Builder month(String month) {
            this.month = month;
            return this;
        }

        public Builder expenses(List<Expense> expenses) {
            this.expenses = expenses;
            return this;
        }

        public ExpensesGroupByYearAndMonth build() {
            return new ExpensesGroupByYearAndMonth(this);
        }
    }
}
