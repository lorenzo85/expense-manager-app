package com.spring.cms.service;

import com.spring.cms.persistence.domain.PaymentState;
import com.spring.cms.service.dto.ExpenseDto;
import com.spring.cms.service.dto.YardDto;
import com.spring.cms.service.exceptions.EntityNotFoundException;
import com.spring.cms.service.expense.ExpenseService;
import com.spring.cms.service.yard.YardService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import static com.spring.cms.persistence.domain.ExpenseCategory.CHECKS;
import static com.spring.cms.persistence.domain.PaymentState.PAID;
import static com.spring.cms.persistence.domain.PaymentState.UNPAID;
import static java.math.RoundingMode.HALF_EVEN;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.junit.Assert.*;

public class ExpenseServiceTest extends BaseServiceTest {

    @Autowired
    ExpenseService service;
    @Autowired
    YardService yardService;

    YardDto yard;
    ExpenseDto expenseDto;

    Date expiresAt;
    Date emissionAt;

    public ExpenseServiceTest() throws ParseException {
        expiresAt = DATE_FORMATTER.parse("01/12/2014");
        emissionAt = DATE_FORMATTER.parse("20/3/2015");
    }

    @Before
    public void setup() {
        BigDecimal contractAmount = createAmount(120324.23);
        yard = createYardDto("yard 1", "Yard 1 description", contractAmount);
        yard = yardService.save(yard);

        BigDecimal amount = createAmount(124325.23);
        expenseDto = createExpenseDto(yard.getId(), 3459, "A title", "A note", amount, expiresAt, emissionAt, PAID, CHECKS);
    }

    @Test
    public void thatSaveExpenseIsBeingAssignedAnIdAndAllParameters() throws ParseException {
        // Given
        ExpenseDto expected = expenseDto;

        // When
        ExpenseDto persisted = service.save(expected);

        // Then
        assertNotNull(persisted.getId());

        ExpenseDto actual = service.findOne(persisted.getId());
        assertTrue(reflectionEquals(expected, actual, "id"));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void thatSaveExpenseThrowsExceptionIfYardIdDoesNotExists() throws ParseException {
        // Given
        long unexistentYardId = yard.getId() - 1;

        ExpenseDto expected = expenseDto;
        expected.setYardId(unexistentYardId);

        // Expect
        service.save(expected);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void thatSaveExpenseThrowsExceptionIfPaymentStateIsNull() {
        // Given
        PaymentState state = null;
        ExpenseDto expected = expenseDto;
        expected.setStatus(state);

        // Expect
        service.save(expected);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void thatSaveExpenseThrowsExceptionIfTitleIsNull() {
        // Given
        String title = null;
        ExpenseDto expected = expenseDto;
        expected.setTitle(title);

        // Expect
        service.save(expected);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void thatSaveExpenseThrowsExceptionWhenAmountIsNull() {
        // Given
        ExpenseDto expected = expenseDto;
        expected.setAmount(null);

        // Expect
        service.save(expected);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void thatSaveExpenseThrowsExceptionWhenAmountTooHigh() {
        // Given
        ExpenseDto expected = expenseDto;
        expected.setAmount(new BigDecimal(12345678912.23));

        // Expect
        service.save(expected);
    }

    @Test
    public void thatSaveExpenseDoesNotThrowExceptionWhenAmountValueIsAtItsLimit() {
        // Given
        ExpenseDto expected = expenseDto;
        BigDecimal amount = new BigDecimal(9999999999.99);
        amount = amount.setScale(2, HALF_EVEN);
        expected.setAmount(amount);

        // When
        ExpenseDto persisted = service.save(expected);

        // Then
        ExpenseDto actual = service.findOne(persisted.getId());
        assertEquals(expected.getAmount(), actual.getAmount());
    }

    @Test
    public void thatSaveExpenseTruncateToTwoFractionalDigits() {
        // Given
        ExpenseDto expected = expenseDto;
        BigDecimal amount = new BigDecimal(2435.2213);
        amount = amount.setScale(4, HALF_EVEN);
        expected.setAmount(amount);

        // When
        expected = service.save(expected);

        // Then
        ExpenseDto persisted = service.findOne(expected.getId());

        // The following truncates the amount as MySQL insertion would!
        // Basically here we are checking that the constraint on the schema
        // definition is correct.
        BigDecimal expectedTruncatedAmount = amount.setScale(2, HALF_EVEN);
        assertEquals(expectedTruncatedAmount, persisted.getAmount());
    }

    @Test
    public void thatSaveExpenseStoresProperlyValueWhenTooFewFractionalDigits() {
        // Given
        ExpenseDto expected = expenseDto;
        BigDecimal amount = new BigDecimal(252.2);
        amount = amount.setScale(1, HALF_EVEN);
        expected.setAmount(amount);

        // When
        expected = service.save(expected);

        // Then
        ExpenseDto persisted = service.findOne(expected.getId());

        BigDecimal expectedAmount = new BigDecimal(252.20).setScale(2, HALF_EVEN);
        assertEquals(expectedAmount, persisted.getAmount());
    }

    @Test
    public void thatSaveExpenseSetsNoteToEmptyWhenNotProvided() {
        // Given
        String note = null;
        ExpenseDto expected = expenseDto;
        expected.setNote(note);

        // When
        ExpenseDto persisted = service.save(expected);

        // Then
        ExpenseDto actual = service.findOne(persisted.getId());
        assertNull(actual.getNote());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void thatExceptionIsThrownWhenTitleStringIsTooLong() {
        // Given
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i <= 200; i++) {
            builder.append('a');
        }

        ExpenseDto expected = expenseDto;
        expected.setTitle(builder.toString());

        // Expect
        service.save(expected);
    }

    @Test
    public void thatUpdateExpenseProperlyUpdatesAllParameters() throws ParseException {
        // Given
        ExpenseDto expected = service.save(expenseDto);

        expected.setStatus(UNPAID);
        expected.setNote(expenseDto.getNote() + "B");
        expected.setTitle(expenseDto.getTitle() + "A");
        expected.setInvoiceId(expenseDto.getInvoiceId() + 100);

        BigDecimal newAmount = createAmount(expenseDto.getAmount().doubleValue() + 10);
        expected.setAmount(newAmount);

        Date newDate = DATE_FORMATTER.parse("11/12/2005");
        expected.setExpiresAt(newDate);

        // When
        service.update(expected);

        // Then
        ExpenseDto actual = service.findOne(expected.getId());

        assertTrue(reflectionEquals(expected, actual,  "expiresAt"));
        assertEquals(newDate.getTime(), actual.getExpiresAt().getTime());
    }

    @Test
    public void thatUpdateExpenseDoesNotCreateNewExpense() {
        // Given
        ExpenseDto expected = service.save(expenseDto);
        long oldCount = service.count();

        // When
        expected.setTitle("A new title");
        service.update(expected);

        // Then
        long newCount = service.count();
        assertEquals(oldCount, newCount);
    }

    @Test(expected = EntityNotFoundException.class)
    public void thatUpdateExpenseThrowsExceptionIfIdDoesNotExists() {
        // Given
        ExpenseDto expected = expenseDto;
        expected.setId(0);

        // Expect
        service.update(expected);
    }

    @Test(expected = EntityNotFoundException.class)
    public void thatDeleteExpenseProperlyDeleteEntity() {
        // Given
        ExpenseDto toBeDeleted = service.save(expenseDto);

        // When
        service.delete(toBeDeleted.getId());

        // Then expect
        service.findOne(toBeDeleted.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void thatSaveDoesNotUpdateExistingExpenseWhenIdMatchesPersistedId() {
        // Given
        ExpenseDto aDto = expenseDto;
        aDto = service.save(aDto);

        ExpenseDto aNewExpense = expenseDto;
        aNewExpense.setId(aDto.getId());

        // When
        service.save(aNewExpense);
    }
}