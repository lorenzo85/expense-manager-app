package org.cms.core.yard;

import org.cms.core.commons.PaymentCollectionMathBuilder;
import org.cms.core.income.Income;
import org.cms.core.expense.Expense;
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

    protected Money sumOfUnPaidExpenses(CurrencyUnit currency) {
        return PaymentCollectionMathBuilder
                .sum()
                .unpaid()
                .on(expenses)
                .compute(currency);
    }

    protected Money sumOfPaidIncomes(CurrencyUnit currency) {
        return PaymentCollectionMathBuilder
                .sum()
                .paid()
                .on(incomes)
                .compute(currency);
    }

    protected Money getReturn(CurrencyUnit currency) {
        return sumOfPaidIncomes(currency)
                .minus(sumOfPaidExpenses(currency));
    }

    protected Money getIncomesToBePaid(CurrencyUnit currency) {
        return contractTotalAmount
                .minus(sumOfPaidIncomes(currency));
    }
}
