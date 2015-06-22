package org.cms.core.commons;

import com.google.common.base.Preconditions;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.Collection;

import static org.cms.core.commons.PaymentCollectionMathBuilder.MathOperator.SUM;

public class PaymentCollectionMathBuilder {

    private MathOperator operator;
    private PaymentState paymentState;
    private Collection<? extends Payment> amounts;

    private PaymentCollectionMathBuilder(MathOperator operator) {
        this.operator = operator;
    }

    public static PaymentCollectionMathBuilder sum() {
        return new PaymentCollectionMathBuilder(SUM);
    }

    public PaymentCollectionMathBuilder unpaid() {
        this.paymentState = PaymentState.UNPAID;
        return this;
    }
    public PaymentCollectionMathBuilder paid() {
        this.paymentState = PaymentState.PAID;
        return this;
    }

    public PaymentCollectionMathBuilder on(Collection<? extends Payment> amounts) {
        this.amounts = amounts;
        return this;
    }

    public Money compute(CurrencyUnit currency) {
        Preconditions.checkArgument(currency != null);
        Preconditions.checkArgument(paymentState != null);

        if(operator == SUM) {
            return amounts.stream()
                    .filter(x -> x.getStatus() == paymentState)
                    .map(Payment::getAmount)
                    .reduce(Money.of(currency, 0), Money::plus);
        }

        throw new IllegalArgumentException(String.format("No matching strategy for operator=%s and payment state=%s", operator, paymentState));
    }

    enum MathOperator {
        SUM
    }
}
