package org.cms.data.domain;


import org.cms.data.converter.PaymentStateConverter;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.joda.money.Money;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="income")
public class Income implements Amount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;
    @Column(name="invoice_id")
    protected long invoiceId;
    @Column(name="status")
    @Convert(converter = PaymentStateConverter.class)
    protected PaymentState status;
    @Column(name="note")
    protected String note;
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
