package org.cms.service.expense;

import org.cms.service.AbstractBaseServiceTest;
import org.cms.service.yard.YardDto;
import org.joda.money.Money;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.cms.service.expense.ExpenseCategory.CHECKS;
import static org.cms.service.expense.ExpenseCategory.SALARIES;
import static org.cms.service.commons.PaymentState.UNPAID;
import static org.cms.service.commons.PaymentAggregator.DateFormatter.MONTH_FORMATTER;
import static org.cms.service.commons.PaymentAggregator.DateFormatter.YEAR_FORMATTER;
import static org.joda.money.Money.of;
import static org.junit.Assert.*;



public class ExpenseServiceMonthlyDeadlinesTest extends AbstractBaseServiceTest {

    @Autowired
    private ExpenseService expenseService;

    YardDto yardDto;

    Money amount1;
    Money amount2;
    Date january2099;
    Date february2099;
    ExpenseCategory CATEGORY_CHECKS;
    ExpenseCategory CATEGORY_SALARIES;

    @Before
    public void setup() throws ParseException {
        CATEGORY_CHECKS = CHECKS;
        CATEGORY_SALARIES = SALARIES;

        amount2 = of(currency, 2.2);
        amount1 = of(currency, 234.23);

        january2099 = DATE_FORMATTER.parse("1/1/2099");
        february2099 = DATE_FORMATTER.parse("12/2/2099");

        yardDto = persistYardDto("A name", "A description", of(currency, 0));
    }

    @Test
    public void thatExpensesInSameYearAndMonthAreGroupedTogether() throws ParseException {
        // Given
        persistUnpaidExpenseDtoWithAmountAndCategory(amount1, CATEGORY_CHECKS, january2099);
        persistUnpaidExpenseDtoWithAmountAndCategory(amount2, CATEGORY_CHECKS, january2099);

        // When
        List<DeadlinesDto> deadlines = expenseService.listDeadlinesGroupedByYearAndMonth();

        // Then
        DeadlinesDto January2099Deadlines = findDeadlineForYearAndMonth(deadlines, january2099);
        assertEquals(2, January2099Deadlines.getExpenses().size());
    }

    @Test
    public void thatExpensesInSameYearAndDifferentMonthsAreGroupedTogether() throws ParseException {
        // Given
        persistUnpaidExpenseDtoWithAmountAndCategory(amount1, CATEGORY_CHECKS, january2099);
        persistUnpaidExpenseDtoWithAmountAndCategory(amount2, CATEGORY_CHECKS, february2099);

        // When
        List<DeadlinesDto> deadlines = expenseService.listDeadlinesGroupedByYearAndMonth();

        // Then
        findDeadlineForYearAndMonth(deadlines, january2099);
        findDeadlineForYearAndMonth(deadlines, february2099);
    }

    @Test
    public void thatDeadlinesTotalIsComputedCorrectly() throws ParseException {
        // Given
        persistUnpaidExpenseDtoWithAmountAndCategory(amount1, CATEGORY_CHECKS, january2099);
        persistUnpaidExpenseDtoWithAmountAndCategory(amount2, CATEGORY_SALARIES, january2099);

        // When
        List<DeadlinesDto> deadlines = expenseService.listDeadlinesGroupedByYearAndMonth();

        // Then
        DeadlinesDto dto = findDeadlineForYearAndMonth(deadlines, january2099);
        Money expected = amount1.plus(amount2);
        assertEquals(expected, dto.getTotal());
    }

    @Test
    public void thatDeadlinesTotalForCategoryIsComputedCorrectly() throws ParseException {
        // Given
        Money amount3 = of(currency, 234.2);

        persistUnpaidExpenseDtoWithAmountAndCategory(amount1, CATEGORY_CHECKS, january2099);
        persistUnpaidExpenseDtoWithAmountAndCategory(amount2, CATEGORY_CHECKS, january2099);
        persistUnpaidExpenseDtoWithAmountAndCategory(amount3, CATEGORY_SALARIES, january2099);

        // When
        List<DeadlinesDto> deadlines = expenseService.listDeadlinesGroupedByYearAndMonth();

        // Then
        DeadlinesDto dto = findDeadlineForYearAndMonth(deadlines, january2099);
        Money checksSum = dto.getExpensesSumsForCategory().get(CATEGORY_CHECKS);

        Money expected = amount1.plus(amount2);
        assertEquals(expected, checksSum);
    }

    @Test
    public void thatDeadlinesTotalForCategoryIsNullIfNoExpenses() throws ParseException {
        // Given
        persistUnpaidExpenseDtoWithAmountAndCategory(amount1, CATEGORY_SALARIES, january2099);

        // When
        List<DeadlinesDto> deadlines = expenseService.listDeadlinesGroupedByYearAndMonth();

        // Then
        DeadlinesDto dto = findDeadlineForYearAndMonth(deadlines, january2099);
        Money checksSum = dto.getExpensesSumsForCategory().get(CATEGORY_CHECKS);

        assertNull(checksSum);
    }

    private ExpenseDto persistUnpaidExpenseDtoWithAmountAndCategory(Money amount, ExpenseCategory category, Date expiryDate) throws ParseException {
        Date emissionAt = DATE_FORMATTER.parse("3/2/1997");
        ExpenseDto expenseDto = new ExpenseDto(234L, "A title", amount, UNPAID, category, expiryDate, emissionAt);
        expenseDto.setYardId(yardDto.getId());
        return expenseService.save(expenseDto);
    }

    private DeadlinesDto findDeadlineForYearAndMonth(List<DeadlinesDto> deadlines, Date date) {
        String year = YEAR_FORMATTER.format(date);
        String month = MONTH_FORMATTER.format(date);
        List<DeadlinesDto> found = filterForYearAndMonth(deadlines, year, month);

        if(found.isEmpty()) {
            fail(format("Could not find deadline for year=%s, month=%s", year, month));
        }

        return found.get(0);
    }

    private List<DeadlinesDto> filterForYearAndMonth(List<DeadlinesDto> deadlines, String year, String month) {
        return deadlines
                .stream()
                .filter(deadline -> {
                    boolean sameYear = year.equals(deadline.getYear());
                    boolean sameMonth = month.equals(deadline.getMonth());
                    return sameYear && sameMonth;
                }).collect(toList());
    }
}