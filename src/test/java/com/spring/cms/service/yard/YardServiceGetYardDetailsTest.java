package com.spring.cms.service.yard;

import com.spring.cms.service.AbstractBaseServiceTest;
import com.spring.cms.service.dto.*;
import com.spring.cms.service.exceptions.EntityNotFoundException;
import org.joda.money.Money;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Collection;

import static com.spring.cms.persistence.domain.ExpenseCategory.MORTGAGES;
import static com.spring.cms.persistence.domain.ExpenseCategory.OTHER;
import static com.spring.cms.persistence.domain.PaymentState.PAID;
import static com.spring.cms.persistence.domain.PaymentState.UNPAID;
import static java.lang.Long.MAX_VALUE;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.junit.Assert.*;


public class YardServiceGetYardDetailsTest extends AbstractBaseServiceTest {

    YardDto yard;
    ExpenseDto paidExpense;
    ExpenseDto unpaidExpense;

    @Before
    public void setup() throws ParseException {
        yard = persistYardDto("A yard name", "A yard description", createAmount(86573.1));
        paidExpense = persistExpenseDto(yard.getId(), 324324, "An expense1 title", "An expense 1 note", createAmount(234.23), DATE_FORMATTER.parse("12/01/2013"), DATE_FORMATTER.parse("1/1/2000"), PAID, MORTGAGES);
        unpaidExpense = persistExpenseDto(yard.getId(), 2535, "An expense2 title", "An expense 2 note", createAmount(354.23), DATE_FORMATTER.parse("1/06/2005"), DATE_FORMATTER.parse("11/2/1990"), UNPAID, OTHER);
    }

    @Test(expected = EntityNotFoundException.class)
    public void thatGetYardDetailsThrowsExceptionIfYardNotFound() {
        // Given
        long unexistentId = MAX_VALUE;

        // When
        yardService.getYardDetails(unexistentId);
    }

    // This test checks that the base YardDto parameters are correct!
    @Test
    public void thatGetYardDetailsReturnsCorrectBaseYardDto() {
        // Given
        YardDto dto = persistYardDto("A name", "A description", createAmount(23.1));

        // When
        YardDto found = yardService.getYardDetails(dto.getId());

        // Then
        assertTrue(reflectionEquals(dto, found, "summary", "expenses", "incomes"));
    }

    @Test
    public void thatGetYardDetailsHasAllCollectionsEmpty() {
        // Given
        YardDto dto = persistYardDto("A name", "A description", createAmount(23.23));

        // When
        ExtendedYardDto found = yardService.getYardDetails(dto.getId());

        // Then
        assertTrue(found.getExpenses().isEmpty());
        assertTrue(found.getIncomes().isEmpty());
    }

    @Test
    public void thatGetYardDetailsHasTotalsAndIncomesNotNull() {
        // Given
        YardDto dto = persistYardDto("A name", "A description", createAmount(3453.23));

        // When
        ExtendedYardDto found = yardService.getYardDetails(dto.getId());

        // Then
        YardSummaryDto summary = found.getSummary();
        assertNotNull(summary.getDeltaPaid());
        assertNotNull(summary.getPaidIncomes());
        assertNotNull(summary.getPaidExpenses());
        assertNotNull(summary.getUnPaidExpenses());
        assertNotNull(summary.getDeltaMissingIncome());
    }

    @Test
    public void thatGetYardDetailsHasAllExpenses() throws ParseException {
        // Given
        YardDto yard = this.yard;

        // When
        ExtendedYardDto extendedYardDto = yardService.getYardDetails(yard.getId());

        // Then
        Collection<ExpenseDto> expenses = extendedYardDto.getExpenses();
        assertEquals(2, expenses.size());
        assertContains(expenses, paidExpense);
        assertContains(expenses, unpaidExpense);
    }

    @Test
    public void thatGetYardDetailsHasAllIncomes() {
        // Given
        YardDto dto = this.yard;
        IncomeDto incomeDto1 = persistIncomeDto(dto.getId(), 3454, UNPAID, createAmount(242423.23), "A note");
        IncomeDto incomeDto2 = persistIncomeDto(dto.getId(), 90723, PAID, createAmount(234234), "A note income");

        // When
        ExtendedYardDto extendedYardDto = yardService.getYardDetails(yard.getId());

        // Then
        Collection<IncomeDto> incomes = extendedYardDto.getIncomes();
        assertEquals(2, incomes.size());
        assertContains(incomes, incomeDto1);
        assertContains(incomes, incomeDto2);
    }

    @Test
    public void thatTotalIncomesPaidIsComputedCorrectly() {
        // Given
        double incomePaidAmount1 = 560750.92;
        double incomePaidAmount2 = 34123.02;

        YardDto dto = this.yard;

        persistIncomeDto(dto.getId(), 3454, PAID, createAmount(incomePaidAmount1), "A note");
        persistIncomeDto(dto.getId(), 90723, UNPAID, createAmount(234234), "A note income");
        persistIncomeDto(dto.getId(), 322, PAID, createAmount(incomePaidAmount2), "A desc");

        // When
        ExtendedYardDto extendedYardDto = yardService.getYardDetails(yard.getId());
        Money actual = extendedYardDto.getSummary().getPaidIncomes();

        // Then
        Money expected = createAmount(incomePaidAmount1).plus(incomePaidAmount2);
        assertEquals(expected, actual);
    }
}