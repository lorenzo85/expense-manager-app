package com.spring.cms.service.dto;

import com.spring.cms.persistence.domain.PaymentState;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class IncomeDto {

    private long id;
    private long yardId;

    @NotNull
    @Min(0)
    @Digits(integer= 11, fraction= 0)
    protected Long invoiceId;
    @NotNull
    @Digits(integer=10, fraction=2)
    @Min(0)
    private BigDecimal amount;
    @NotNull
    private PaymentState status;
    @NotNull
    private String note;

    public IncomeDto() {}

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

    public void setAmount(BigDecimal amount) {
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

    public BigDecimal getAmount() {
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

}
