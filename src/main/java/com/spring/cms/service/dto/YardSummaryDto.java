package com.spring.cms.service.dto;

import java.math.BigDecimal;

public class YardSummaryDto {
    private BigDecimal deltaPaid;
    private BigDecimal paidIncomes;
    private BigDecimal paidExpenses;
    private BigDecimal unPaidExpenses;
    private BigDecimal deltaMissingIncome;

    public YardSummaryDto(BigDecimal paidIncomes, BigDecimal paidExpenses,
                          BigDecimal unPaidExpenses, BigDecimal deltaPaid, BigDecimal deltaMissingIncome) {
        this.deltaPaid = deltaPaid;
        this.paidIncomes = paidIncomes;
        this.paidExpenses = paidExpenses;
        this.unPaidExpenses = unPaidExpenses;
        this.deltaMissingIncome = deltaMissingIncome;
    }

    public BigDecimal getDeltaPaid() {
        return deltaPaid;
    }

    public BigDecimal getPaidIncomes() {
        return paidIncomes;
    }

    public BigDecimal getPaidExpenses() {
        return paidExpenses;
    }

    public BigDecimal getUnPaidExpenses() {
        return unPaidExpenses;
    }

    public BigDecimal getDeltaMissingIncome() {
        return deltaMissingIncome;
    }
}
