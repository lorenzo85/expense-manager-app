package com.spring.cms.service.analysis;

import com.spring.cms.persistence.domain.Amount;
import com.spring.cms.persistence.domain.PaymentState;

import java.math.BigDecimal;
import java.util.Collection;

public class SumStrategy extends AbstractAnalysisStrategy implements AnalysisStrategy {

    private PaymentState paymentState;
    private Collection<? extends Amount> incomes;

    public SumStrategy(PaymentState paymentState, Collection<? extends Amount> incomes) {
        this.incomes = incomes;
        this.paymentState = paymentState;
    }

    @Override
    public BigDecimal execute() {
        return getSumFor(paymentState, incomes);
    }
}
