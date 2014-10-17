package com.spring.cms.service.analysis;

import com.spring.cms.persistence.domain.Amount;
import com.spring.cms.persistence.domain.PaymentState;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.Collection;

public class SumStrategy extends AbstractAnalysisStrategy implements AnalysisStrategy {

    private CurrencyUnit currency;
    private PaymentState paymentState;
    private Collection<? extends Amount> incomes;

    public SumStrategy(PaymentState paymentState, Collection<? extends Amount> incomes, CurrencyUnit currency) {
        this.incomes = incomes;
        this.currency = currency;
        this.paymentState = paymentState;
    }

    @Override
    public Money execute() {
        return getSumFor(paymentState, incomes, currency);
    }
}