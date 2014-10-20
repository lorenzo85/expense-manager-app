package com.spring.cms.service.expense;

import com.spring.cms.service.AbstractBaseValidationServiceTest;
import com.spring.cms.service.dto.ExpenseDto;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import static com.spring.cms.persistence.domain.ExpenseCategory.CHECKS;
import static com.spring.cms.persistence.domain.PaymentState.PAID;
import static java.util.Arrays.asList;
import static org.joda.money.CurrencyUnit.USD;
import static org.joda.money.Money.of;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
                { new ExpenseDto(null, "A title", of(USD, 560), PAID, CHECKS, date1, date2), IllegalArgumentException.class },
                { new ExpenseDto(-1L, "A title", of(USD, 560), PAID, CHECKS, date1, date2), IllegalArgumentException.class },
                { new ExpenseDto(111111111111L, "A title", of(USD, 560), PAID, CHECKS, date1, date2), IllegalArgumentException.class },
                { new ExpenseDto(234L, null, of(USD, 560), PAID, CHECKS, date1, date2), IllegalArgumentException.class },
                { new ExpenseDto(234L, "", of(USD, 560), PAID, CHECKS, date1, date2), IllegalArgumentException.class },
                { new ExpenseDto(234L, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", of(USD, 560), PAID, CHECKS, date1, date2), IllegalArgumentException.class },
                { new ExpenseDto(234L, "A title", null, PAID, CHECKS, date1, date2), IllegalArgumentException.class },
                { new ExpenseDto(234L, "A title", of(USD, 560), null, CHECKS, date1, date2), IllegalArgumentException.class },
                { new ExpenseDto(234L, "A title", of(USD, 560), PAID, null, date1, date2), IllegalArgumentException.class },
                { new ExpenseDto(234L, "A title", of(USD, 560), PAID, CHECKS, null, date2), IllegalArgumentException.class },
                { new ExpenseDto(234L, "A title", of(USD, 560), PAID, CHECKS, date1, null), IllegalArgumentException.class },
        });
    }

    @Test
    public void thatIllegalArgumentExceptionIsThrownWhenSaveInvalidDto() {
        // Expect
        try {
            expenseService.save(this.dto);
            fail("Should have thrown=" + this.expectedException + " exception.");
        } catch (Exception e) {
            assertEquals(expectedException, e.getClass());
        }
    }

    @Test
    public void thatIllegalArgumentExceptionIsThrownWhenUpdateInvalidDto() {
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