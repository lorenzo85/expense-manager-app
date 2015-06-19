package org.cms.service.expense;

import org.cms.service.yard.Yard;
import org.dozer.DozerBeanMapper;
import org.joda.money.Money;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

import static java.lang.System.currentTimeMillis;
import static java.util.Collections.singletonList;
import static org.cms.service.ConfigurationService.DOZER_MAPPER_SPEC;
import static org.cms.service.commons.PaymentState.PAID;
import static org.cms.service.commons.PaymentState.UNPAID;
import static org.cms.service.expense.ExpenseCategory.CHECKS;
import static org.cms.service.expense.ExpenseCategory.MORTGAGES;
import static org.joda.money.CurrencyUnit.AUD;
import static org.joda.money.Money.of;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class ExpenseConverterTest {

    DozerBeanMapper mapper;
    Random random;

    @Before
    public void setUp() {
        mapper = new DozerBeanMapper();
        mapper.setMappingFiles(singletonList(DOZER_MAPPER_SPEC));
        random = new Random();
    }

    @Test
    public void shouldMapCorrectlyToExpenseDto() {
        // Given
        Yard yard = new Yard();
        setField(yard, "id", random.nextLong());

        Expense expense = new Expense();
        expense.yard = yard;
        expense.status = UNPAID;
        expense.category = MORTGAGES;
        expense.id = random.nextLong();
        expense.amount = of(AUD, 23.43);
        expense.note = "An expense note";
        expense.title = "An expense title";
        expense.invoiceId = random.nextLong();
        expense.expiresAt = new Timestamp(currentTimeMillis());
        expense.emissionAt = new Timestamp(currentTimeMillis() + 20);

        // When
        ExpenseDto dto = mapper.map(expense, ExpenseDto.class);

        // Then
        assertEquals(getField(expense.yard, "id"), dto.getYardId());
        assertEquals(expense.status, dto.getStatus());
        assertEquals(expense.category, dto.getCategory());
        assertEquals(expense.id, dto.getId());
        assertEquals(expense.amount, dto.getAmount());
        assertEquals(expense.note, dto.getNote());
        assertEquals(expense.title, dto.getTitle());
        assertEquals(expense.invoiceId, dto.getInvoiceId().longValue());
        assertEquals(expense.expiresAt.compareTo(dto.getExpiresAt()), 0);
        assertEquals(expense.emissionAt.compareTo(dto.getEmissionAt()),0);
    }

    @Test
    public void shouldMapCorrectlyToExpense() {
        // Given
        ExpenseDto dto = new ExpenseDto();
        dto.setYardId(random.nextLong());
        dto.setStatus(PAID);
        dto.setCategory(CHECKS);
        dto.setId(random.nextLong());
        dto.setAmount(Money.of(AUD, 234.23));
        dto.setNote("A note");
        dto.setTitle("A title");
        dto.setInvoiceId(random.nextLong());
        dto.setExpiresAt(new Date(currentTimeMillis() + 10));
        dto.setExpiresAt(new Date(currentTimeMillis() + 40));

        // When
        Expense expense = mapper.map(dto, Expense.class);

        // Then
        assertEquals(dto.getYardId(), getField(expense.yard, "id"));
    }
}
