package org.cms.service.income;

import org.cms.service.commons.PaymentState;
import org.cms.service.commons.Dto;
import org.joda.money.Money;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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

    public IncomeDto() {
    }

    public IncomeDto(long id) {
        this.id = id;
    }

    public long getYardId() {
        return yardId;
    }

    public void setYardId(long yardId) {
        this.yardId = yardId;
    }

    public long getId() {
        return id;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setStatus(PaymentState status) {
        this.status = status;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public Money getAmount() {
        return amount;
    }

    public PaymentState getStatus() {
        return status;
    }

    public String getNote() {
        return note;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public Long getIdentifier() {
        return id;
    }
}
