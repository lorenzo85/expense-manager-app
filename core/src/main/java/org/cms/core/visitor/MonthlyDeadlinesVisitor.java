package org.cms.core.visitor;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.cms.core.deadline.DeadlinesExpensesForCategoryTotals;
import org.cms.core.expense.Expense;
import org.cms.core.income.Income;
import org.joda.money.CurrencyUnit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.*;
import static java.util.Locale.ENGLISH;
import static org.cms.core.visitor.MonthlyDeadlinesVisitor.DateFormatter.*;


public class MonthlyDeadlinesVisitor implements Visitor {

    private Map<ImmutablePair, DeadlinesExpensesForCategoryTotals> DEADLINES = new HashMap<>();
    private CurrencyUnit currencyUnit;

    public MonthlyDeadlinesVisitor(CurrencyUnit currencyUnit) {
        this.currencyUnit = currencyUnit;
    }

    @Override
    public void visit(Expense expense) {
        Date expiresAt = expense.getExpiresAt();
        ImmutablePair<String, String> pair = createPair(expiresAt);
        if (!DEADLINES.containsKey(pair)) {
            DEADLINES.put(pair, new DeadlinesExpensesForCategoryTotals(pair.getLeft(), pair.getRight(), currencyUnit));
        }
        DeadlinesExpensesForCategoryTotals dto = DEADLINES.get(pair);
        dto.getExpenses().add(expense);
        dto.addToTotal(expense.getAmount());
        dto.addToTotalForCategory(expense.getAmount(), expense.getCategory());
    }

    @Override
    public void visit(Income income) {
        throw new UnsupportedOperationException(format("Visiting an %s is not supported.",
                Income.class.getSimpleName()));
    }

    public Map<ImmutablePair, DeadlinesExpensesForCategoryTotals> getDEADLINES() {
        return DEADLINES;
    }

    private ImmutablePair<String, String> createPair(Date expiresAt) {
        String year = YEAR_FORMATTER.format(expiresAt);
        String month = MONTH_FORMATTER.format(expiresAt);
        return new ImmutablePair<>(year, month);
    }

    public enum DateFormatter {
        MONTH_FORMATTER("MMMM"),
        YEAR_FORMATTER("yyyy");

        DateFormat format;

        DateFormatter(String pattern) {
            this.format = new SimpleDateFormat(pattern, ENGLISH);
        }

        public String format(Date date) {
            return format.format(date);
        }
    }
}
