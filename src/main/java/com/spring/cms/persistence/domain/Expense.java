package com.spring.cms.persistence.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name="expense")
public class Expense implements Amount {

    @Id
    @GeneratedValue(strategy = AUTO)
    protected long id;
    @Column(name="invoice_id")
    protected long invoiceId;
    @Column(name="title")
    protected String title;
    @Column(name="note")
    protected String note;
    @Column(name="amount")
    protected BigDecimal amount;
    @Column(name="status")
    @Convert(converter = PaymentStateConverter.class)
    protected PaymentState status;
    @Column(name="category")
    @Convert(converter = ExpenseCategoryConverter.class)
    protected ExpenseCategory category;
    @Column(name="emission_at")
    protected Timestamp emissionAt;
    @Column(name="expires_at")
    protected Timestamp expiresAt;

    @ManyToOne
    @JoinColumn(name="yard_id")
    protected Yard yard;

    public Expense() {}

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PaymentState getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setStatus(PaymentState status) {
        this.status = status;
    }

    public void setYard(Yard yard) {
        this.yard = yard;
    }

    public Yard getYard() {
        return yard;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setExpiresAt(Timestamp expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Timestamp getExpiresAt() {
        return expiresAt;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public long getInvoiceId() {
        return invoiceId;
    }

    public Timestamp getEmissionAt() {
        return emissionAt;
    }

    public void setEmissionAt(Timestamp emissionAt) {
        this.emissionAt = emissionAt;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }
}