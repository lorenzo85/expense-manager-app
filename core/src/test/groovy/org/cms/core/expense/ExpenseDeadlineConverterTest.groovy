package org.cms.core.expense
import org.cms.core.BaseSpecification
import org.cms.core.deadline.DeadlinesExpenseDto
import org.cms.core.deadline.DeadlinesExpensesForCategoryTotals
import org.dozer.DozerBeanMapper

import static PaymentCategory.CHECKS
import static java.util.Collections.singletonList
import static org.cms.core.ConfigurationService.DOZER_MAPPER_SPEC
import static org.joda.money.CurrencyUnit.EUR

class ExpenseDeadlineConverterTest extends BaseSpecification {

    def mapper = new DozerBeanMapper()

    void setup() {
        mapper.setMappingFiles(singletonList(DOZER_MAPPER_SPEC))
    }

    def "Should convert to dto"() {
        given:
        Expense expense = new Expense()
        expense.amount = amountOf(23.43)
        expense.category = CHECKS
        expense.id = 20
        def expenseGroup = new DeadlinesExpensesForCategoryTotals("2004", "Jan", EUR, singletonList(expense))
        expenseGroup.computeSumsForEachExpenseCategory()

        when:
        def dto = mapper.map(expenseGroup, DeadlinesExpenseDto.class)

        then:
        1 == dto.getExpenses().size()
        1 == dto.getExpensesSumsForCategory().size()
        dto.getExpensesSumsForCategory().containsKey(CHECKS)
    }

}
