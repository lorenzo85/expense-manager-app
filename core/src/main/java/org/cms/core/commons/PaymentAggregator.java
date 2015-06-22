package org.cms.core.commons;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.Locale.ENGLISH;
import static java.util.stream.Collectors.groupingBy;
import static org.cms.core.commons.PaymentAggregator.DateFormatter.*;

public class PaymentAggregator {

    private PaymentAggregator() {
    }

    public static <T extends Payment> Map<Pair<String, String>, List<T>> groupByYearAndMonth(List<T> payments) {
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
