package org.cms.core.deadline;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.cms.core.commons.Payment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.Locale.ENGLISH;
import static java.util.stream.Collectors.groupingBy;
import static org.cms.core.deadline.Deadline.DateFormatter.MONTH_FORMATTER;
import static org.cms.core.deadline.Deadline.DateFormatter.YEAR_FORMATTER;

public class Deadline {

    protected <T extends Payment> Map<Pair<String, String>, List<T>> groupByYearAndMonth(List<T> payments) {
        return payments.stream()
                .collect(groupingBy(payment -> {
                    Date expiresAt = payment.getExpiresAt();
                    return createPair(expiresAt);
                }));
    }

    private ImmutablePair createPair(Date expiresAt) {
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
