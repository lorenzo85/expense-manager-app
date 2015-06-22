package org.cms.core.expense;


import org.cms.core.AbstractBaseServiceTest;
import org.cms.core.yard.YardDto;
import org.cms.core.commons.EntityNotFoundException;
import org.joda.money.Money;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;

import java.text.ParseException;
import java.util.Date;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.cms.core.expense.ExpenseCategory.CHECKS;
import static org.cms.core.commons.PaymentState.PAID;
import static org.cms.core.commons.PaymentState.UNPAID;
import static org.junit.Assert.*;

public class ExpenseServiceTest extends AbstractBaseServiceTest {

    Date expiresAt = DATE_FORMATTER.parse("01/12/2014");
    Date emissionAt = DATE_FORMATTER.parse("20/3/2015");
    YardDto yard;
    ExpenseDto expenseDto;

    public ExpenseServiceTest() throws ParseException {
    }

    @Before
    public void setup() {
        Money contractAmount = createAmount(120324.23);
        yard = createYardDto("yard 1", "Yard 1 description", contractAmount);
        yard = yardService.save(yard);

        Money amount = createAmount(124325.23);
        expenseDto = createExpenseDto(yard.getId(), 3459, "A title", "A note", amount, expiresAt, emissionAt, PAID, CHECKS);
    }

    @Test
    public void thatSaveExpenseIsBeingAssignedAnIdAndAllParameters() throws ParseException {
        // Given
        ExpenseDto expected = expenseDto;

        // When
        ExpenseDto persisted = expenseService.save(expected);

        // Then
        assertNotNull(persisted.getId());

        ExpenseDto actual = expenseService.findOne(persisted.getId());
        assertTrue(reflectionEquals(expected, actual, "id"));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void thatSaveExpenseThrowsExceptionIfYardIdDoesNotExists() throws ParseException {
        // Given
        long unexistentYardId = yard.getId() - 1;

        ExpenseDto expected = expenseDto;
        expected.setYardId(unexistentYardId);

        // Expect
        expenseService.save(expected);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void thatSaveExpenseThrowsExceptionWhenAmountTooHigh() {
        // Given
        ExpenseDto expected = expenseDto;
        expected.setAmount(createAmount(123456789123456789232323.23));

        // Expect
        expenseService.save(expected);
    }

    @Test
    public void thatSaveExpenseDoesNotThrowExceptionWhenAmountValueIsAtItsLimit() {
        // Given
        ExpenseDto expected = expenseDto;
        expected.setAmount(createAmount(9999999999.99));

        // When
        ExpenseDto persisted = expenseService.save(expected);

        // Then
        ExpenseDto actual = expenseService.findOne(persisted.getId());
        assertEquals(expected.getAmount(), actual.getAmount());
    }

    @Test
    public void thatSaveExpenseSetsNoteToEmptyWhenNotProvided() {
        // Given
        String note = null;
        ExpenseDto expected = expenseDto;
        expected.setNote(note);

        // When
        ExpenseDto persisted = expenseService.save(expected);

        // Then
        ExpenseDto actual = expenseService.findOne(persisted.getId());
        assertNull(actual.getNote());
    }

    @Test
    public void thatUpdateExpenseProperlyUpdatesAllParameters() throws ParseException {
        // Given
        ExpenseDto expected = expenseService.save(expenseDto);

        expected.setStatus(UNPAID);
        expected.setNote(expenseDto.getNote() + "B");
        expected.setTitle(expenseDto.getTitle() + "A");
        expected.setInvoiceId(expenseDto.getInvoiceId() + 100);

        Money newAmount = expenseDto.getAmount().plus(createAmount(0.2));
        expected.setAmount(newAmount);

        Date newDate = DATE_FORMATTER.parse("11/12/2005");
        expected.setExpiresAt(newDate);

        // When
        expenseService.update(expected);

        // Then
        ExpenseDto actual = expenseService.findOne(expected.getId());

        assertTrue(reflectionEquals(expected, actual,  "expiresAt"));
        assertEquals(newDate.getTime(), actual.getExpiresAt().getTime());
    }

    @Test
    public void thatUpdateExpenseDoesNotCreateNewExpense() {
        // Given
        ExpenseDto expected = expenseService.save(expenseDto);
        long oldCount = expenseService.count();

        // When
        expected.setTitle("A new title");
        expenseService.update(expected);

        // Then
        long newCount = expenseService.count();
        assertEquals(oldCount, newCount);
    }

    @Test(expected = EntityNotFoundException.class)
    public void thatUpdateExpenseThrowsExceptionIfIdDoesNotExists() {
        // Given
        ExpenseDto expected = expenseDto;
        expected.setId(0L);

        // Expect
        expenseService.update(expected);
    }

    @Test(expected = EntityNotFoundException.class)
    public void thatDeleteExpenseProperlyDeleteEntity() {
        // Given
        ExpenseDto toBeDeleted = expenseService.save(expenseDto);

        // When
        expenseService.delete(toBeDeleted.getId());

        // Then expect
        expenseService.findOne(toBeDeleted.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void thatSaveDoesNotUpdateExistingExpenseWhenIdMatchesPersistedId() {
        // Given
        ExpenseDto aDto = expenseDto;
        aDto = expenseService.save(aDto);

        ExpenseDto aNewExpense = expenseDto;
        aNewExpense.setId(aDto.getId());

        // When
        expenseService.save(aNewExpense);
    }
}