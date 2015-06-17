package org.cms.data.dto;

import org.cms.data.domain.PaymentState;
import org.cms.data.domain.ExpenseCategory;
import org.joda.money.Money;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

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

    public ExpenseDto() {
    }

    public ExpenseDto(long id) {
        this.id = id;
    }

    public ExpenseDto(Long invoiceId, String title, Money amount, PaymentState status,
                      ExpenseCategory category, Date expiresAt, Date emissionAt) {
        this.invoiceId = invoiceId;
        this.title = title;
        this.amount = amount;
        this.status = status;
        this.category = category;
        this.expiresAt = expiresAt;
        this.emissionAt = emissionAt;
    }

    public long getYardId() {
        return yardId;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }

    public Money getAmount() {
        return amount;
    }

    public PaymentState getStatus() {
        return status;
    }

    public Date getExpiresAt() {
        return this.expiresAt;
    }

    public void setYardId(long yardId) {
        this.yardId = yardId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public void setStatus(PaymentState status) {
        this.status = status;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Date getEmissionAt() {
        return emissionAt;
    }

    public void setEmissionAt(Date emissionAt) {
        this.emissionAt = emissionAt;
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    @Override
    public Long getIdentifier() {
        return id;
    }
}
