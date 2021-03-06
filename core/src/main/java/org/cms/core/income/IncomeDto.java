package org.cms.core.income;

import lombok.*;
import org.cms.core.commons.PaymentState;
import org.cms.core.commons.Dto;
import org.joda.money.Money;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncomeDto implements Dto<Long> {

    private long id;
    private long yardId;

    @NotNull
    @Min(0)
    @Digits(integer= 11, fraction= 0)
    protected Long invoiceId;
    @NotNull
    private Money amount;
    @NotNull
    private PaymentState status;
    @NotNull
    private String note;


    @Override
    public Long getId() {
        return id;
    }

}
