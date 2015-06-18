package org.cms.service.yard;

import com.google.common.base.Preconditions;
import org.cms.service.commons.Amount;
import org.cms.service.commons.PaymentState;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.Collection;

import static org.cms.service.yard.YardSummaryBuilder.MathOperator.SUM;

public class YardSummaryBuilder {

    private MathOperator operator;
    private PaymentState paymentState;
    private Collection<? extends Amount> amounts;

    private YardSummaryBuilder(MathOperator operator) {
        this.operator = operator;
    }

    public static YardSummaryBuilder sum() {
        return new YardSummaryBuilder(SUM);
    }

    public YardSummaryBuilder unpaid() {
        this.paymentState = PaymentState.UNPAID;
        return this;
    }
    public YardSummaryBuilder paid() {
        this.paymentState = PaymentState.PAID;
        return this;
    }

    public YardSummaryBuilder on(Collection<? extends Amount> amounts) {
        this.amounts = amounts;
        return this;
    }

    public Money compute(CurrencyUnit currency) {
        Preconditions.checkArgument(currency != null);
        Preconditions.checkArgument(paymentState != null);

        if(operator == SUM) {
            return amounts.stream()
                    .filter(x -> x.getStatus() == paymentState)
                    .map(Amount::getAmount)
                    .reduce(Money.of(currency, 0), Money::plus);
        }

        throw new IllegalArgumentException(String.format("No matching strategy for operator=%s and payment state=%s", operator, paymentState));
    }

    enum MathOperator {
        SUM
    }
}
