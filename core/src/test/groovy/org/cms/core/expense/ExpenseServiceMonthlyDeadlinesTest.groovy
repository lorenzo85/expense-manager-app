package org.cms.core.expense
import org.cms.core.BaseSpecification

import java.text.ParseException

import static org.cms.core.commons.PaymentState.UNPAID
import static org.cms.core.deadline.Deadline.DateFormatter.MONTH_FORMATTER
import static org.cms.core.deadline.Deadline.DateFormatter.YEAR_FORMATTER
import static org.cms.core.expense.ExpenseCategory.*

class ExpenseServiceMonthlyDeadlinesTest extends BaseSpecification {

    def amount1
    def amount2
    def january2099
    def january2011
    def february2099
    def yardDto
    def year2099
    def year2011
    def monthJanuary
    def monthFebruary

    void setup() {
        amount2 = amountOf(2.2)
        amount1 = amountOf(234.23)
        january2099 = DATE_FORMATTER.parse("1/1/2099")
        february2099 = DATE_FORMATTER.parse("12/2/2099")
        january2011 = DATE_FORMATTER.parse("1/1/2011")
        yardDto = saveYard("A name", "A description", amountOf(0))
        year2099 = YEAR_FORMATTER.format(january2099)
        year2011 = YEAR_FORMATTER.format(january2011)
        monthJanuary = MONTH_FORMATTER.format(january2099)
        monthFebruary = MONTH_FORMATTER.format(february2099)
    }

    def "Should group together expenses in the same year and month"() {
        given:
        persistUnpaidExpenseDtoWithAmountAndCategory(amount1, CHECKS, january2099)
        persistUnpaidExpenseDtoWithAmountAndCategory(amount2, CHECKS, january2099)

        when:
        def deadlines = deadlineService.listDeadlinesGroupedByYearAndMonth()
        def found = deadlines.findAll { deadline -> deadline.year == year2099 && deadline.month == monthJanuary}


        then:
        found.size() == 1
        found.get(0).expenses.size() == 2
    }

    def "Should not group together expenses in the same year with different month"() {
        given:
        persistUnpaidExpenseDtoWithAmountAndCategory(amount1, MORTGAGES, january2099)
        persistUnpaidExpenseDtoWithAmountAndCategory(amount2, CHECKS, february2099)

        when:
        def deadlines = deadlineService.listDeadlinesGroupedByYearAndMonth()
        def found = deadlines.findAll { deadline ->
            deadline.year == year2099 && deadline.month == monthJanuary ||
                    deadline.year == year2099 && deadline.month == monthFebruary
        }

        then:
        found.size() == 2
        found.get(0).expenses.size() == 1
        found.get(1).expenses.size() == 1
    }

    def "Should not group together expenses in different year with same month"() {
        given:
        persistUnpaidExpenseDtoWithAmountAndCategory(amount1, MORTGAGES, january2011)
        persistUnpaidExpenseDtoWithAmountAndCategory(amount2, CHECKS, january2099)

        when:
        def deadlines = deadlineService.listDeadlinesGroupedByYearAndMonth()
        def found = deadlines.findAll { deadline ->
            deadline.year == year2099 && deadline.month == monthJanuary ||
                    deadline.year == year2011 && deadline.month == monthJanuary
        }

        then:
        found.size() == 2
        found.get(0).expenses.size() == 1
        found.get(1).expenses.size() == 1
    }

    def "Should compute total correctly"() {
        given:
        persistUnpaidExpenseDtoWithAmountAndCategory(amount1, CHECKS, january2099)
        persistUnpaidExpenseDtoWithAmountAndCategory(amount2, MORTGAGES, january2099)

        when:
        def deadlines = deadlineService.listDeadlinesGroupedByYearAndMonth()
        def found = deadlines.findAll {deadline -> deadline.year == year2099 && deadline.month == monthJanuary}

        then:
        found.get(0).total == amount1.plus(amount2)
    }

    def "Should compute total for category correctly"() {
        given:
        def amount3 = amountOf(2343.23)
        persistUnpaidExpenseDtoWithAmountAndCategory(amount1, CHECKS, january2099);
        persistUnpaidExpenseDtoWithAmountAndCategory(amount2, CHECKS, january2099);
        persistUnpaidExpenseDtoWithAmountAndCategory(amount3, SALARIES, january2099);

        when:
        def deadlines = deadlineService.listDeadlinesGroupedByYearAndMonth()
        def found = deadlines.findAll {deadline -> deadline.year == year2099 && deadline.month == monthJanuary}

        then:
        found.get(0).expensesSumsForCategory.get(CHECKS) == amount1.plus(amount2)
    }

    def "Should return null for category if no expenses for that category"() {
        given:
        persistUnpaidExpenseDtoWithAmountAndCategory(amount1, SALARIES, january2099);

        when:
        def deadlines = deadlineService.listDeadlinesGroupedByYearAndMonth()
        def found = deadlines.findAll {deadline -> deadline.year == year2099 && deadline.month == monthJanuary}

        then:
        found.get(0).expensesSumsForCategory.get(CHECKS) == null

    }

    def persistUnpaidExpenseDtoWithAmountAndCategory(amount, category, expiryDate) throws ParseException {
        Date emissionAt = DATE_FORMATTER.parse("3/2/1997");
        ExpenseDto expenseDto = ExpenseDto.builder()
                .emissionAt(emissionAt)
                .expiresAt(expiryDate)
                .amount(amount)
                .status(UNPAID)
                .title("A title")
                .category(category)
                .invoiceId(234L)
                .yardId(yardDto.getId())
                .build();
        expenseService.save(expenseDto);
    }
}
