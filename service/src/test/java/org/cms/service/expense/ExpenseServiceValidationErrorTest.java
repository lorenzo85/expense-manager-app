package org.cms.service.expense;


import org.cms.service.AbstractBaseValidationServiceTest;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import static java.util.Arrays.asList;
import static org.cms.service.expense.ExpenseCategory.CHECKS;
import static org.cms.service.commons.PaymentState.PAID;
import static org.joda.money.CurrencyUnit.USD;
import static org.joda.money.Money.of;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


@Ignore
public class ExpenseServiceValidationErrorTest extends AbstractBaseValidationServiceTest {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);

    @Autowired
    ExpenseService expenseService;

    ExpenseDto dto;
    Class expectedException;

    public ExpenseServiceValidationErrorTest(ExpenseDto dto, Class expectedException) throws ParseException{
        this.dto = dto;
        this.expectedException = expectedException;

    }

    @Parameterized.Parameters
    public static Collection dtos() throws ParseException {
        Date date1 = DATE_FORMATTER.parse("12/2/2011");
        Date date2 = DATE_FORMATTER.parse("1/10/1998");

        return asList(new Object[][]{
                { new ExpenseDto(null, "A title", of(USD, 560), PAID, CHECKS, date1, date2), InvalidDataAccessApiUsageException.class },
                { new ExpenseDto(-1L, "A title", of(USD, 560), PAID, CHECKS, date1, date2), InvalidDataAccessApiUsageException.class },
                { new ExpenseDto(111111111111L, "A title", of(USD, 560), PAID, CHECKS, date1, date2), InvalidDataAccessApiUsageException.class },
                { new ExpenseDto(234L, null, of(USD, 560), PAID, CHECKS, date1, date2), InvalidDataAccessApiUsageException.class },
                { new ExpenseDto(234L, "", of(USD, 560), PAID, CHECKS, date1, date2), InvalidDataAccessApiUsageException.class },
                { new ExpenseDto(234L, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", of(USD, 560), PAID, CHECKS, date1, date2), InvalidDataAccessApiUsageException.class },
                { new ExpenseDto(234L, "A title", null, PAID, CHECKS, date1, date2), InvalidDataAccessApiUsageException.class },
                { new ExpenseDto(234L, "A title", of(USD, 560), null, CHECKS, date1, date2), InvalidDataAccessApiUsageException.class },
                { new ExpenseDto(234L, "A title", of(USD, 560), PAID, null, date1, date2), InvalidDataAccessApiUsageException.class },
                { new ExpenseDto(234L, "A title", of(USD, 560), PAID, CHECKS, null, date2), InvalidDataAccessApiUsageException.class },
                { new ExpenseDto(234L, "A title", of(USD, 560), PAID, CHECKS, date1, null), InvalidDataAccessApiUsageException.class },
        });
    }

    @Test
    public void thatInvalidDataAccessApiUsageExceptionIsThrownWhenSaveInvalidDto() {
        // Expect
        try {
            expenseService.save(this.dto);
            fail("Should have thrown=" + this.expectedException + " exception.");
        } catch (Exception e) {
            assertEquals(expectedException, e.getClass());
        }
    }

    @Test
    public void thatInvalidDataAccessApiUsageExceptionIsThrownWhenUpdateInvalidDto() {
        // Expect
        try {
            expenseService.update(this.dto);
            fail("Should have thrown=" + this.expectedException + " exception.");
        } catch (Exception e) {
            assertEquals(expectedException, e.getClass());
        }
    }

    @Override
    protected Class<?> getTestClass() {
        return getClass();
    }
}