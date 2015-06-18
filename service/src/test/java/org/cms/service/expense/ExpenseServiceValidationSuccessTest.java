package org.cms.service.expense;


import org.cms.service.AbstractBaseServiceTest;
import org.cms.service.yard.YardDto;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.cms.service.expense.ExpenseCategory.CHECKS;
import static org.cms.service.commons.PaymentState.PAID;
import static org.joda.money.CurrencyUnit.EUR;
import static org.joda.money.Money.of;
import static org.junit.Assert.assertEquals;

public class ExpenseServiceValidationSuccessTest extends AbstractBaseServiceTest {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);


    @Autowired
    ExpenseService expenseService;

    ExpenseDto expenseDto;

    @Before
    public void setup() throws ParseException {
        YardDto yardDto = persistYardDto("A name", "A description", of(EUR, 23.43));

        Date date1 = DATE_FORMATTER.parse("23/1/1990");
        Date date2 = DATE_FORMATTER.parse("11/9/1999");

        expenseDto = new ExpenseDto(234L, "A title", of(EUR, 560), PAID, CHECKS, date1, date2);
        expenseDto.setYardId(yardDto.getId());
    }

    @Test
    public void thatTitleAtItsLimitIsPersisted() {
        // Given
        String maxTitleLength = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        expenseDto.setTitle(maxTitleLength);
        assertEquals(200, maxTitleLength.length());

        // When
        ExpenseDto persisted = expenseService.save(expenseDto);

        // Then
        ExpenseDto actual = expenseService.findOne(persisted.getId());
        assertEquals(maxTitleLength, actual.getTitle());
    }

    @Test
    public void thatTitleWithLengthOneIsPersisted() {
        // Given
        String title = "a";
        expenseDto.setTitle(title);

        // When
        ExpenseDto persisted = expenseService.save(expenseDto);

        // Then
        ExpenseDto actual = expenseService.findOne(persisted.getId());
        assertEquals(title, actual.getTitle());
    }
}