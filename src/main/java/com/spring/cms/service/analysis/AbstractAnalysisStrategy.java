package com.spring.cms.service.analysis;

import com.spring.cms.persistence.domain.Amount;
import com.spring.cms.persistence.domain.PaymentState;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.Collection;

import static org.joda.money.Money.of;


public abstract class AbstractAnalysisStrategy implements AnalysisStrategy {

    protected Money getSumFor(PaymentState paymentState, Collection<? extends Amount> amounts, CurrencyUnit currency) {
        Money sum = of(currency, 0);
        for (Amount amount : amounts) {
            if (amount.getStatus() == paymentState) {
                sum = updateSum(sum, amount);
            }
        }
        return sum;
    }

    private Money updateSum(Money sum, Amount amount) {
        if (sum == null) {
            return of(amount.getAmount());
        } else {
            return sum.plus(amount.getAmount());
        }
    }
}
