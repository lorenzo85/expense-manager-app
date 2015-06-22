package org.cms.core.expense;

import lombok.*;
import org.cms.core.commons.PaymentState;
import org.cms.core.commons.Dto;
import org.joda.money.Money;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDto implements Dto<Long> {

    private long id;
    private long yardId;
    private String note;

    @NotNull
    @Min(0)
    @Digits(integer= 11, fraction= 0)
    protected Long invoiceId;
    @NotNull
    @Size(min= 1, max= 200)
    private String title;
    @NotNull
    private Money amount;
    @NotNull
    private PaymentState status;
    @NotNull
    private ExpenseCategory category;
    @NotNull
    private Date expiresAt;
    @NotNull
    private Date emissionAt;

    @Override
    public Long getId() {
        return id;
    }

}
