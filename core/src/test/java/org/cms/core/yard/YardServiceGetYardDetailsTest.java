package org.cms.core.yard;

import org.cms.core.AbstractBaseServiceTest;
import org.cms.core.commons.PaymentState;
import org.cms.core.expense.ExpenseDto;
import org.cms.core.income.IncomeDto;
import org.joda.money.Money;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

import static org.cms.core.commons.PaymentState.PAID;
import static org.cms.core.commons.PaymentState.UNPAID;
import static org.cms.core.expense.ExpenseCategory.MORTGAGES;
import static org.cms.core.expense.ExpenseCategory.OTHER;
import static org.joda.money.CurrencyUnit.EUR;
import static org.joda.money.Money.of;
import static org.junit.Assert.*;


public class YardServiceGetYardDetailsTest extends AbstractBaseServiceTest {

    YardDto yard;

    @Before
    public void setup() throws ParseException {
        yard = persistYardDto("A yard name", "A yard description", createAmount(86573.1));
    }

    // TODO: This belongs to yard details test
    @Test
    public void thatGetYardDetailsHasAllCollectionsEmpty() {
        // Given
        YardDto dto = persistYardDto("A name", "A description", createAmount(23.23));

        // When
        YardExtendedDto found = yardService.getYardDetails(dto.getId());

        // Then
        assertTrue(found.getExpenses().isEmpty());
        assertTrue(found.getIncomes().isEmpty());
    }

    // TODO: This belongs to YardDetails test
    @Test
    public void thatGetYardDetailsHasTotalsAndIncomesNotNull() {
        // Given
        YardDto dto = persistYardDto("A name", "A description", createAmount(3453.23));

        // When
        YardExtendedDto found = yardService.getYardDetails(dto.getId());

        // Then
        YardSummaryDto summary = found.getSummary();
        assertNotNull(summary.getDeltaPaid());
        assertNotNull(summary.getPaidIncomes());
        assertNotNull(summary.getPaidExpenses());
        assertNotNull(summary.getUnPaidExpenses());
        assertNotNull(summary.getDeltaMissingIncome());
    }

    // TODO: This belongs to YardDetails test
    @Test
    public void thatGetYardDetailsHasAllExpenses() throws ParseException {
        // Given
        YardDto yard = this.yard;
        ExpenseDto paidExpense = persistExpenseDto(yard.getId(), 324324, "An expense1 title", "An expense 1 note", createAmount(234.23), DATE_FORMATTER.parse("12/01/2013"), DATE_FORMATTER.parse("1/1/2000"), PAID, MORTGAGES);
        ExpenseDto unpaidExpense = persistExpenseDto(yard.getId(), 2535, "An expense2 title", "An expense 2 note", createAmount(354.23), DATE_FORMATTER.parse("1/06/2005"), DATE_FORMATTER.parse("11/2/1990"), UNPAID, OTHER);

        // When
        YardExtendedDto yardExtendedDto = yardService.getYardDetails(yard.getId());

        // Then
        Collection<ExpenseDto> expenses = yardExtendedDto.getExpenses();
        assertEquals(2, expenses.size());
        assertContains(expenses, paidExpense);
        assertContains(expenses, unpaidExpense);
    }

    // TODO: This belongs to YardDetails test
    @Test
    public void thatGetYardDetailsHasAllIncomes() {
        // Given
        YardDto dto = this.yard;
        IncomeDto incomeDto1 = persistIncomeDto(dto.getId(), 3454, UNPAID, createAmount(242423.23), "A note");
        IncomeDto incomeDto2 = persistIncomeDto(dto.getId(), 90723, PAID, createAmount(234234), "A note income");

        // When
        YardExtendedDto yardExtendedDto = yardService.getYardDetails(yard.getId());

        // Then
        Collection<IncomeDto> incomes = yardExtendedDto.getIncomes();
        assertEquals(2, incomes.size());
        assertContains(incomes, incomeDto1);
        assertContains(incomes, incomeDto2);
    }

    // TODO: This belongs to YardDetails test
    @Test
    public void thatTotalPaidIncomesIsComputedCorrectly() {
        // Given
        YardDto dto = this.yard;

        Money amount1 = of(EUR, 45.23);
        Money amount2 = of(EUR, 500.23);
        Money amount3 = of(EUR, 1250);

        persistIncomeDtoWithAmount(amount1, PAID, dto.getId());
        persistIncomeDtoWithAmount(amount2, PAID, dto.getId());
        persistIncomeDtoWithAmount(amount3, UNPAID, dto.getId());

        // When
        YardExtendedDto yardExtendedDto = yardService.getYardDetails(dto.getId());
        Money actual = yardExtendedDto.getSummary().getPaidIncomes();

        // Then
        Money expected = amount1.plus(amount2);
        assertEquals(expected, actual);
    }

    @Test
    public void thatTotalPaidIncomesIsZeroWhenNoIncomes() {
        // Given
        YardDto dto = this.yard;

        Money amount1 = of(EUR, 23423);

        persistIncomeDtoWithAmount(amount1, UNPAID, dto.getId());

        // When
        YardExtendedDto yardExtendedDto = yardService.getYardDetails(dto.getId());
        Money actual = yardExtendedDto.getSummary().getPaidIncomes();

        // Then
        assertEquals(of(EUR, 0), actual);
    }

    @Test
    public void thatTotalPaidExpensesIsComputedCorrectly() throws ParseException {
        // Given
        YardDto dto = this.yard;

        Money amount1 = of(EUR, 12.23);
        Money amount2 = of(EUR, 234324);
        Money amount3 = of(EUR, 234234.23);

        persistExpenseDtoWithAmount(amount1, PAID, dto.getId());
        persistExpenseDtoWithAmount(amount2, UNPAID, dto.getId());
        persistExpenseDtoWithAmount(amount3, PAID, dto.getId());

        // When
        YardExtendedDto yardExtendedDto = yardService.getYardDetails(dto.getId());
        Money actual = yardExtendedDto.getSummary().getPaidExpenses();

        // Then
        Money expected = amount1.plus(amount3);
        assertEquals(expected, actual);
    }

    @Test
    public void thatTotalUnPaidExpensesIsComputedCorrectly() throws ParseException {
        // Given
        YardDto dto = this.yard;

        Money amount1 = of(EUR, 342.23);
        Money amount2 = of(EUR, 4325.23);
        Money amount3 = of(EUR, 12.3);

        persistExpenseDtoWithAmount(amount1, UNPAID, dto.getId());
        persistExpenseDtoWithAmount(amount2, PAID, dto.getId());
        persistExpenseDtoWithAmount(amount3, UNPAID, dto.getId());

        // When
        YardExtendedDto yardExtendedDto = yardService.getYardDetails(dto.getId());
        Money actual = yardExtendedDto.getSummary().getUnPaidExpenses();

        // Then
        Money expected = amount1.plus(amount3);
        assertEquals(expected, actual);
    }

    @Test
    public void thatDeltaPaidIsComputedCorrectly() throws ParseException {
        // Given
        YardDto dto = this.yard;

        Money expenseAmount1 = of(EUR, 234.23);
        Money expenseAmount2 = of(EUR, 98);
        Money incomeAmount1 = of(EUR, 234);
        Money incomeAmount2 = of(EUR, 987);
        Money incomeAmount3 = of(EUR, 23423423);

        persistExpenseDtoWithAmount(expenseAmount1, PAID, dto.getId());
        persistExpenseDtoWithAmount(expenseAmount2, UNPAID, dto.getId());
        persistIncomeDtoWithAmount(incomeAmount1, UNPAID, dto.getId());
        persistIncomeDtoWithAmount(incomeAmount2, PAID, dto.getId());
        persistIncomeDtoWithAmount(incomeAmount3, PAID, dto.getId());

        // When
        YardExtendedDto yardExtendedDto = yardService.getYardDetails(dto.getId());
        Money actual = yardExtendedDto.getSummary().getDeltaPaid();

        // Then
        Money expected = incomeAmount2.plus(incomeAmount3).minus(expenseAmount1);
        assertEquals(expected, actual);
    }

    @Test
    public void thatDeltaMissingIncomeIsComputedCorrectly() {
        // Given
        YardDto dto = this.yard;

        Money incomeAmount1 = of(EUR, 234.23);
        Money incomeAmount2 = of(EUR, 2342223423L);
        Money incomeAmount3 = of(EUR, 23);

        persistIncomeDtoWithAmount(incomeAmount1, PAID, dto.getId());
        persistIncomeDtoWithAmount(incomeAmount2, UNPAID, dto.getId());
        persistIncomeDtoWithAmount(incomeAmount3, PAID, dto.getId());

        // When
        YardExtendedDto yardExtendedDto = yardService.getYardDetails(dto.getId());
        Money actual = yardExtendedDto.getSummary().getDeltaMissingIncome();

        // Then
        Money expected = dto.getContractTotalAmount().minus(incomeAmount1.plus(incomeAmount3));
        assertEquals(actual, expected);
    }

    private ExpenseDto persistExpenseDtoWithAmount(Money amount, PaymentState state, long yardId) throws ParseException {
        Date date1 = DATE_FORMATTER.parse("30/9/1999");
        Date date2 = DATE_FORMATTER.parse("12/1/2000");
        return persistExpenseDto(yardId, 234L, "A title", "A note", amount, date1, date2, state, MORTGAGES);
    }

    private IncomeDto persistIncomeDtoWithAmount(Money amount, PaymentState state, long yardId) {
        return persistIncomeDto(yardId, 3454, state, amount, "A note");
    }
}