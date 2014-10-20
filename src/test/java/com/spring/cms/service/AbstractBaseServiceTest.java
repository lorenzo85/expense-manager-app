package com.spring.cms.service;

import com.spring.cms.persistence.domain.ExpenseCategory;
import com.spring.cms.persistence.domain.PaymentState;
import com.spring.cms.service.dto.ExpenseDto;
import com.spring.cms.service.dto.IncomeDto;
import com.spring.cms.service.dto.YardDto;
import com.spring.cms.service.expense.ExpenseService;
import com.spring.cms.service.income.IncomeService;
import com.spring.cms.service.yard.YardService;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.junit.Assert.fail;

public abstract class AbstractBaseServiceTest extends AbstractBaseTest {

    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);

    @Autowired
    protected CurrencyUnit currency;

    @Autowired
    public YardService yardService;
    @Autowired
    public IncomeService incomeService;
    @Autowired
    public ExpenseService expenseService;

    protected <T> void assertContains(Collection<T> entities, T entity) {
        for (T e : entities) {
            if(reflectionEquals(entity, e)) return;
        }

        fail("Could not find element in the collection!");
    }

    protected Money createAmount(double value) {
        return Money.of(currency, value);
    }

    protected YardDto persistYardDto(String name, String description, Money contractAmount) {
        YardDto dto = createYardDto(name, description, contractAmount);
        return yardService.save(dto);
    }

    protected ExpenseDto persistExpenseDto(long yardId, long invoiceId, String title, String note, Money amount,
                                           Date expiresAt, Date emissionAt, PaymentState state,
                                           ExpenseCategory category) {
        ExpenseDto dto = createExpenseDto(yardId, invoiceId, title, note, amount, expiresAt, emissionAt, state, category);
        return expenseService.save(dto);
    }

    protected IncomeDto persistIncomeDto(long yardId, long invoiceId, PaymentState state, Money amount, String note) {
        IncomeDto dto = createIncomeDto(yardId, invoiceId, state, amount, note);
        return incomeService.save(dto);
    }

    protected YardDto createYardDto(String name, String description, Money contractAmount) {
        YardDto dto = new YardDto();
        dto.setName(name);
        dto.setDescription(description);
        dto.setContractTotalAmount(contractAmount);
        return dto;
    }

    protected ExpenseDto createExpenseDto(long yardId, long invoiceId, String title,
                                          String note, Money amount, Date expiresAt, Date emissionAt,
                                          PaymentState state, ExpenseCategory category) {
        ExpenseDto dto = new ExpenseDto();
        dto.setNote(note);
        dto.setTitle(title);
        dto.setStatus(state);
        dto.setYardId(yardId);
        dto.setAmount(amount);
        dto.setCategory(category);
        dto.setExpiresAt(expiresAt);
        dto.setInvoiceId(invoiceId);
        dto.setEmissionAt(emissionAt);
        return dto;
    }

    protected IncomeDto createIncomeDto(long yardId, long invoiceId, PaymentState status, Money amount, String note) {
        IncomeDto dto = new IncomeDto();
        dto.setNote(note);
        dto.setYardId(yardId);
        dto.setStatus(status);
        dto.setAmount(amount);
        dto.setInvoiceId(invoiceId);
        return dto;
    }
}