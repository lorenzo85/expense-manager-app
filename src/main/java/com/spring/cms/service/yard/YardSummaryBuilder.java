package com.spring.cms.service.yard;

import com.spring.cms.persistence.domain.Amount;
import com.spring.cms.persistence.domain.PaymentState;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkArgument;
import static com.spring.cms.persistence.domain.PaymentState.PAID;
import static com.spring.cms.persistence.domain.PaymentState.UNPAID;
import static com.spring.cms.service.yard.YardSummaryBuilder.MathOperator.SUM;
import static java.lang.String.format;
import static org.joda.money.Money.of;

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
        this.paymentState = UNPAID;
        return this;
    }
    public YardSummaryBuilder paid() {
        this.paymentState = PAID;
        return this;
    }

    public YardSummaryBuilder on(Collection<? extends Amount> amounts) {
        this.amounts = amounts;
        return this;
    }

    public Money compute(CurrencyUnit currency) {
        checkArgument(currency != null);
        checkArgument(paymentState != null);

        if(operator == SUM) {
            return amounts.stream()
                    .filter(x -> x.getStatus() == paymentState)
                    .map(Amount::getAmount)
                    .reduce(of(currency, 0), (x, y) -> x.plus(y));
        }

        throw new IllegalArgumentException(format("No matching strategy for operator=%s and payment state=%s", operator, paymentState));
    }

    enum MathOperator {
        SUM
    }
}
