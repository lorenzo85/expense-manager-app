package com.spring.cms.service.yard;

import com.spring.cms.persistence.domain.Expense;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.joda.money.CurrencyUnit.EUR;

public class YardSummaryBuilderTest {

    @Test(expected = IllegalArgumentException.class)
    public void thatExceptionIsThrownWhenPaymentStatusNotSpecified() {
        // Given
        List<Expense> expenses = Arrays.asList(new Expense());

        // Expect
        YardSummaryBuilder.sum().on(expenses).compute(EUR);
    }
}
