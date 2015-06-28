package org.cms.core.deadline;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.cms.core.commons.Payment;
import org.cms.core.expense.Expense;
import org.joda.money.CurrencyUnit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.Locale.ENGLISH;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.cms.core.deadline.Deadline.DateFormatter.MONTH_FORMATTER;
import static org.cms.core.deadline.Deadline.DateFormatter.YEAR_FORMATTER;

public class Deadline {

    public static List<MonthlyDeadlines> calculateMonthlyDeadlinesForExpenses(List<Expense> payments, CurrencyUnit currencyUnit) {
        Map<Pair<String, String>, List<Expense>> expensesGroupedByYearAndMonth = groupByYearAndMonth(payments);
        return expensesGroupedByYearAndMonth
                .entrySet()
                .stream()
                .map(expensesForYearAndMonth -> {
                    Pair<String, String> key = expensesForYearAndMonth.getKey();
                    return MonthlyDeadlines.builder(currencyUnit)
                            .year(key.getLeft())
                            .month(key.getRight())
                            .expenses(expensesForYearAndMonth.getValue())
                            .build();
                }).collect(toList());
    }

    private static <T extends Payment> Map<Pair<String, String>, List<T>> groupByYearAndMonth(List<T> payments) {
        return payments.stream()
                .collect(groupingBy(payment -> {
                    Date expiresAt = payment.getExpiresAt();
                    return new ImmutablePair<>(
                            YEAR_FORMATTER.format(expiresAt),
                            MONTH_FORMATTER.format(expiresAt));
                }));
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
