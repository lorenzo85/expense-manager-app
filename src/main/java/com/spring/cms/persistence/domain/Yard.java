package com.spring.cms.persistence.domain;

import org.hibernate.annotations.Type;
import org.joda.money.Money;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name="yard")
public class Yard {

    @Id
    @GeneratedValue(strategy = AUTO)
    protected long id;
    @Column(name="name")
    protected String name;
    @Column(name="description")
    protected String description;
    @Column(name="contract_total_amount")
    @Type(type="org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmount",
            parameters = {@org.hibernate.annotations.Parameter(name= "currencyCode", value="EUR")})
    protected Money contractTotalAmount;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "yard")
    @OrderBy("status DESC , expires_at DESC ")
    protected List<Expense> expenses = new ArrayList<Expense>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "yard")
    @OrderBy("status DESC, created_at DESC ")
    protected List<Income> incomes = new ArrayList<Income>();

    public Yard() {
    }

    public Yard(long id) {
        this.id = id;
    }

    public Yard(Yard yard) {
        this(yard.getId(), yard.getName(), yard.getDescription());
        this.incomes = yard.getIncomes();
        this.expenses = yard.getExpenses();
        this.contractTotalAmount = yard.getContractTotalAmount();
    }

    public Yard(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Money getContractTotalAmount() {
        return contractTotalAmount;
    }

    public List<Income> getIncomes() {
        return incomes;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setContractTotalAmount(Money contractTotalAmount) {
        this.contractTotalAmount = contractTotalAmount;
    }

    public void setIncomes(List<Income> incomes) {
        this.incomes = incomes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(long id) {
        this.id = id;
    }

}
