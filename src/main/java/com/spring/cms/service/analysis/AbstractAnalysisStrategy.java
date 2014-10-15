package com.spring.cms.service.analysis;

import com.spring.cms.persistence.domain.Amount;
import com.spring.cms.persistence.domain.PaymentState;

import java.math.BigDecimal;
import java.util.Collection;

import static java.math.RoundingMode.HALF_UP;

public abstract class AbstractAnalysisStrategy implements AnalysisStrategy {

    protected BigDecimal getZeroAmount() {
        return new BigDecimal(0.00).setScale(2, HALF_UP);
    }

    protected BigDecimal getSumFor(PaymentState paymentState, Collection<? extends Amount> amounts) {
        BigDecimal sum = getZeroAmount();
        for(Amount amount : amounts) {
            if(amount.getStatus() == paymentState) {
                sum = sum.add(amount.getAmount());
            }
        }
        return sum;
    }
}
