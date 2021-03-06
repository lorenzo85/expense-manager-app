package org.cms.core.expense;


import org.cms.core.commons.Payment;
import org.cms.core.commons.PaymentState;
import org.cms.core.commons.PaymentStateConverter;
import org.cms.core.visitor.Visitable;
import org.cms.core.visitor.Visitor;
import org.cms.core.yard.Yard;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.joda.money.Money;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name="expense")
public class Expense implements Payment, Visitable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    @Column(name="invoice_id")
    long invoiceId;
    @Column(name="title")
    String title;
    @Column(name="note")
    String note;
    @Column(name="status")
    @Convert(converter = PaymentStateConverter.class)
    PaymentState status;
    @Column(name="category")
    @Convert(converter = ExpenseCategoryConverter.class)
    PaymentCategory category;
    @Column(name="emission_at")
    Timestamp emissionAt;
    @Column(name="expires_at")
    Timestamp expiresAt;
    @Column(name="created_at",
            insertable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP")
    Date createdAt;
    @Column(name="updated_at",
            insertable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    Date updatedAt;
    @Column(name="amount")
    @Type(type="org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmount",
            parameters = {@Parameter(name= "currencyCode", value="EUR")})
    Money amount;
    @ManyToOne
    @JoinColumn(name="yard_id")
    Yard yard;

    @Override
    public Money getAmount() {
        return amount;
    }

    @Override
    public PaymentState getStatus() {
        return status;
    }

    @Override
    public Timestamp getExpiresAt() {
        return expiresAt;
    }

    public PaymentCategory getCategory() {
        return category;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}