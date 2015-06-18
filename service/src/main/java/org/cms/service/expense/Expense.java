package org.cms.service.expense;


import org.cms.service.commons.PaymentStateConverter;
import org.cms.service.commons.Amount;
import org.cms.service.commons.PaymentState;
import org.cms.service.yard.Yard;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.joda.money.Money;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name="expense")
public class Expense implements Amount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;
    @Column(name="invoice_id")
    protected long invoiceId;
    @Column(name="title")
    protected String title;
    @Column(name="note")
    protected String note;
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
    @Column(name="created_at",
            insertable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP")
    protected Date createdAt;
    @Column(name="updated_at",
            insertable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    protected Date updatedAt;
    @Column(name="amount")
    @Type(type="org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmount",
            parameters = {@Parameter(name= "currencyCode", value="EUR")})
    protected Money amount;


    @ManyToOne
    @JoinColumn(name="yard_id")
    protected Yard yard;

    public Expense() {
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