package com.spring.cms.service.analysis;

import com.spring.cms.persistence.domain.Amount;
import com.spring.cms.persistence.domain.PaymentState;

import java.math.BigDecimal;
import java.util.Collection;

import static com.spring.cms.persistence.domain.PaymentState.PAID;
import static com.spring.cms.persistence.domain.PaymentState.UNPAID;
import static com.spring.cms.service.analysis.MathOperator.SUM;
import static java.lang.String.format;

public class AnalysisStrategyBuilder {

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

    public BigDecimal on(Collection<? extends Amount> amounts) {
        this.amounts = amounts;
        return buildAndRun();
    }

    private BigDecimal buildAndRun() {
        if(operator == SUM) {
            return new SumStrategy(paymentState, amounts).execute();

        }
        throw new IllegalArgumentException(format("No matching strategy for operator=%s and payment state=%s", operator, paymentState));
    }
}
