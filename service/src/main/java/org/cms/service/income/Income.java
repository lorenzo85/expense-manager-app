package org.cms.service.income;


import org.cms.service.commons.PaymentStateConverter;
import org.cms.service.commons.Amount;
import org.cms.service.commons.PaymentState;
import org.cms.service.yard.Yard;
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
    long id;
    @Column(name="invoice_id")
    long invoiceId;
    @Column(name="status")
    @Convert(converter = PaymentStateConverter.class)
    PaymentState status;
    @Column(name="note")
    String note;
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
}
