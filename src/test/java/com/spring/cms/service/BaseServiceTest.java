package com.spring.cms.service;


import com.spring.cms.config.PersistenceConfig;
import com.spring.cms.config.ServiceConfig;
import com.spring.cms.persistence.domain.ExpenseCategory;
import com.spring.cms.persistence.domain.PaymentState;
import com.spring.cms.persistence.repository.ExpenseRepository;
import com.spring.cms.persistence.repository.IncomeRepository;
import com.spring.cms.persistence.repository.YardRepository;
import com.spring.cms.service.dto.ExpenseDto;
import com.spring.cms.service.dto.IncomeDto;
import com.spring.cms.service.dto.YardDto;
import com.spring.cms.service.expense.ExpenseService;
import com.spring.cms.service.income.IncomeService;
import com.spring.cms.service.yard.YardService;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import static java.math.RoundingMode.HALF_UP;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, ServiceConfig.class})
public abstract class BaseServiceTest {

    static final String DATE_FORMAT = "dd/MM/yyyy";
    static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);

    @Autowired
    protected YardRepository yardRepo;
    @Autowired
    private IncomeRepository incomeRepo;
    @Autowired
    protected ExpenseRepository expenseRepo;

    @Autowired
    protected YardService service;
    @Autowired
    protected IncomeService incomeService;
    @Autowired
    protected ExpenseService expenseService;

    @After
    public void teardown() {
        expenseRepo.deleteAll();
        incomeRepo.deleteAll();
        yardRepo.deleteAll();
    }

    protected <T> void assertContains(Collection<T> entities, T entity) {
        for (T e : entities) {
            if(reflectionEquals(entity, e)) return;
        }
        fail("Could not find element in the collection!");
    }

    protected BigDecimal createAmount(double value) {
        BigDecimal amount = new BigDecimal(value);
        return amount.setScale(2, HALF_UP);
    }

    protected YardDto persistYardDto(String name, String description, BigDecimal contractAmount) {
        YardDto dto = createYardDto(name, description, contractAmount);
        return service.save(dto);
    }

    protected ExpenseDto persistExpenseDto(long yardId, long invoiceId, String title, String note, BigDecimal amount,
                                           Date expiresAt, Date emissionAt, PaymentState state,
                                           ExpenseCategory category) {
        ExpenseDto dto = createExpenseDto(yardId, invoiceId, title, note, amount, expiresAt, emissionAt, state, category);
        return expenseService.save(dto);
    }

    protected IncomeDto persistIncomeDto(long yardId, long invoiceId, PaymentState state, BigDecimal amount, String note) {
        IncomeDto dto = createIncomeDto(yardId, invoiceId, state, amount, note);
        return incomeService.save(dto);
    }

    protected YardDto createYardDto(String name, String description, BigDecimal contractAmount) {
        YardDto dto = new YardDto();
        dto.setName(name);
        dto.setDescription(description);
        dto.setContractTotalAmount(contractAmount);
        return dto;
    }

    protected ExpenseDto createExpenseDto(long yardId, long invoiceId, String title,
                                          String note, BigDecimal amount, Date expiresAt, Date emissionAt,
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

    protected IncomeDto createIncomeDto(long yardId, long invoiceId,
                                        PaymentState status, BigDecimal amount, String note) {
        IncomeDto dto = new IncomeDto();
        dto.setNote(note);
        dto.setYardId(yardId);
        dto.setStatus(status);
        dto.setAmount(amount);
        dto.setInvoiceId(invoiceId);
        return dto;
    }
}