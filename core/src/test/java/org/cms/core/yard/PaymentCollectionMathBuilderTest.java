package org.cms.core.yard;


import org.cms.core.commons.PaymentCollectionMathBuilder;
import org.cms.core.expense.Expense;
import org.junit.Test;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.joda.money.CurrencyUnit.EUR;

public class PaymentCollectionMathBuilderTest {

    @Test(expected = IllegalArgumentException.class)
    public void thatExceptionIsThrownWhenPaymentStatusNotSpecified() {
        // Given
        List<Expense> expenses = singletonList(new Expense());

        // Expect
        PaymentCollectionMathBuilder.sum().on(expenses).compute(EUR);
    }
}
