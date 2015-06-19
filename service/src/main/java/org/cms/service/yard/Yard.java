package org.cms.service.yard;

import org.cms.service.commons.PaymentCollectionMathBuilder;
import org.cms.service.income.Income;
import org.cms.service.expense.Expense;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="yard")
public class Yard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    @Column(name="name")
    String name;
    @Column(name="description")
    String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "yard")
    @OrderBy("status DESC , expires_at DESC ")
    List<Expense> expenses = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "yard")
    @OrderBy("status DESC, created_at DESC ")
    List<Income> incomes = new ArrayList<>();
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
    @Column(name="contract_total_amount")
    @Type(type="org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmount",
            parameters = {@Parameter(name= "currencyCode", value="EUR")})
    Money contractTotalAmount;


    protected Money sumOfPaidExpenses(CurrencyUnit currency) {
        return PaymentCollectionMathBuilder
                .sum()
                .paid()
                .on(expenses)
                .compute(currency);
    }

    protected Money sumOfUnPaidExpenses(CurrencyUnit currencyUnit) {
        return PaymentCollectionMathBuilder
                .sum()
                .unpaid()
                .on(expenses)
                .compute(currencyUnit);
    }

    protected Money sumOfPaidIncomes(CurrencyUnit currencyUnit) {
        return PaymentCollectionMathBuilder
                .sum()
                .paid()
                .on(incomes)
                .compute(currencyUnit);
    }
}
