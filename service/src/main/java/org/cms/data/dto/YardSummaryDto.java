package org.cms.data.dto;

import org.joda.money.Money;

public class YardSummaryDto {
    private Money deltaPaid;
    private Money paidIncomes;
    private Money paidExpenses;
    private Money unPaidExpenses;
    private Money deltaMissingIncome;

    public YardSummaryDto(Money paidIncomes, Money paidExpenses,
                          Money unPaidExpenses, Money deltaPaid, Money deltaMissingIncome) {
        this.deltaPaid = deltaPaid;
        this.paidIncomes = paidIncomes;
        this.paidExpenses = paidExpenses;
        this.unPaidExpenses = unPaidExpenses;
        this.deltaMissingIncome = deltaMissingIncome;
    }

    public Money getDeltaPaid() {
        return deltaPaid;
    }

    public Money getPaidIncomes() {
        return paidIncomes;
    }

    public Money getPaidExpenses() {
        return paidExpenses;
    }

    public Money getUnPaidExpenses() {
        return unPaidExpenses;
    }

    public Money getDeltaMissingIncome() {
        return deltaMissingIncome;
    }
}
