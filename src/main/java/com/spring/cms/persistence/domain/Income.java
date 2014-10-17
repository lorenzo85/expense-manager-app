package com.spring.cms.persistence.domain;

import org.hibernate.annotations.Type;
import org.joda.money.Money;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name="income")
public class Income implements Amount {

    @Id
    @GeneratedValue(strategy = AUTO)
    protected long id;
    @Column(name="invoice_id")
    protected long invoiceId;
    @Column(name="amount")
    @Type(type="org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmount",
            parameters = {@org.hibernate.annotations.Parameter(name= "currencyCode", value="EUR")})
    protected Money amount;
    @Column(name="status")
    @Convert(converter = PaymentStateConverter.class)
    protected PaymentState status;
    @Column(name="note")
    protected String note;

    @ManyToOne
    @JoinColumn(name="yard_id")
    protected Yard yard;

    public void setYard(Yard yard) {
        this.yard = yard;
    }

    public long getId() {
        return id;
    }

    public Yard getYard() {
        return yard;
    }

    public Money getAmount() {
        return amount;
    }

    public String getNote() {
        return note;
    }

    public long getInvoiceId() {
        return invoiceId;
    }

    public PaymentState getStatus() {
        return status;
    }

    public void setStatus(PaymentState status) {
        this.status = status;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setId(long id) {
        this.id = id;
    }
}
