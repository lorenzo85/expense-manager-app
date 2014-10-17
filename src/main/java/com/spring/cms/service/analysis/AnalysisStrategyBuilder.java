package com.spring.cms.service.analysis;

import com.spring.cms.persistence.domain.Amount;
import com.spring.cms.persistence.domain.PaymentState;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.Collection;

import static com.spring.cms.persistence.domain.PaymentState.PAID;
import static com.spring.cms.persistence.domain.PaymentState.UNPAID;
import static com.spring.cms.service.analysis.MathOperator.SUM;
import static java.lang.String.format;

public class AnalysisStrategyBuilder {

    private CurrencyUnit currency;
    private MathOperator operator;
    private PaymentState paymentState;
    private Collection<? extends Amount> amounts;

    public AnalysisStrategyBuilder(MathOperator operator) {
        this.operator = operator;
    }

    public static AnalysisStrategyBuilder sum() {
        return new AnalysisStrategyBuilder(SUM);
    }

    public AnalysisStrategyBuilder unpaid() {
        this.paymentState = UNPAID;
        return this;
    }
    public AnalysisStrategyBuilder paid() {
        this.paymentState = PAID;
        return this;
    }

    public Money on(Collection<? extends Amount> amounts, CurrencyUnit currency) {
        this.amounts = amounts;
        this.currency = currency;
        return buildAndRun();
    }

    private Money buildAndRun() {
        if(operator == SUM) {
            return new SumStrategy(paymentState, amounts, currency).execute();
        }

        throw new IllegalArgumentException(format("No matching strategy for operator=%s and payment state=%s", operator, paymentState));
    }
}
