package org.cms.service.yard;


import org.cms.service.expense.Expense;
import org.junit.Test;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.joda.money.CurrencyUnit.EUR;

public class YardSummaryBuilderTest {

    @Test(expected = IllegalArgumentException.class)
    public void thatExceptionIsThrownWhenPaymentStatusNotSpecified() {
        // Given
        List<Expense> expenses = singletonList(new Expense());

        // Expect
        YardSummaryBuilder.sum().on(expenses).compute(EUR);
    }
}
