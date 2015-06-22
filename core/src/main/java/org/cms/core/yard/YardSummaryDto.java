package org.cms.core.yard;

import lombok.Getter;
import lombok.Setter;
import org.joda.money.Money;

@Getter
@Setter
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

}
