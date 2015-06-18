package org.cms.service.expense;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.joda.money.Money;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.cms.service.expense.DeadlinesDto.DateFormatter.MONTH_FORMATTER;
import static org.cms.service.expense.DeadlinesDto.DateFormatter.YEAR_FORMATTER;
import static java.util.Locale.ENGLISH;
import static java.util.stream.Collectors.groupingBy;
import static org.joda.money.CurrencyUnit.EUR;
import static org.joda.money.Money.*;

public class DeadlinesDto {

    private String year;
    private String month;
    private Money total = of(EUR, 0);
    private List<ExpenseDto> expenses = new ArrayList<>();
    private Map<ExpenseCategory, Money> mapCategorySums = new HashMap<>();

    public DeadlinesDto(String year, String month, List<ExpenseDto> expenses) {
        this.year = year;
        this.month = month;
        this.expenses = expenses;

        computeStats();
    }

    public void setMapCategorySums(Map<ExpenseCategory, Money> mapCategorySums) {
        this.mapCategorySums = mapCategorySums;
    }

    public void addExpense(ExpenseDto expense) {
        this.expenses.add(expense);
    }

    public List<ExpenseDto> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<ExpenseDto> expenses) {
        this.expenses = expenses;
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public Money getTotal() {
        return total;
    }

    public Map<ExpenseCategory, Money> getMapCategorySums() {
        return mapCategorySums;
    }

    private void computeStats(){
        expenses.forEach(e -> {
            Money amount = e.getAmount();
            total = total.plus(amount);
            mapCategorySums.compute(e.getCategory(), (k, v) -> (v == null) ? amount : v.plus(amount));
        });
    }

    public static List<DeadlinesDto> computeSumAndSumForCategories(List<ExpenseDto> expenses) {
        Map<Pair<String, String>, List<ExpenseDto>> mapYearAndDtos  = groupByYearAndMonth(expenses);
        List<DeadlinesDto> deadlines = new ArrayList<>();
        for (Map.Entry<Pair<String, String>, List<ExpenseDto>> entry : mapYearAndDtos.entrySet()) {
            Pair<String, String> key = entry.getKey();
            deadlines.add(new DeadlinesDto(key.getLeft(), key.getRight(), entry.getValue()));
        }
        return deadlines;
    }

    private static Map<Pair<String, String>, List<ExpenseDto>> groupByYearAndMonth(List<ExpenseDto> expenses) {
        return expenses.stream()
                .collect(groupingBy(expense -> {
                    Date expiresAt = expense.getExpiresAt();
                    return new ImmutablePair<>(YEAR_FORMATTER.format(expiresAt), MONTH_FORMATTER.format(expiresAt));
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