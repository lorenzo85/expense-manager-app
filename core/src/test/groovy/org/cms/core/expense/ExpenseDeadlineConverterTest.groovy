package org.cms.core.expense
import org.cms.core.BaseSpecification
import org.dozer.DozerBeanMapper

import static java.util.Collections.singletonList
import static org.cms.core.ConfigurationService.DOZER_MAPPER_SPEC
import static org.cms.core.expense.ExpenseCategory.CHECKS
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
        def expenseGroup = ExpensesGroupByYearAndMonth.builder(EUR)
                .month("Jan")
                .year("2004")
                .expenses(singletonList(expense))
                .build()

        when:
        def dto = mapper.map(expenseGroup, DeadlinesDto.class)

        then:
        1 == dto.getExpenses().size()
        1 == dto.getExpensesSumsForCategory().size()
        dto.getExpensesSumsForCategory().containsKey(CHECKS)
    }

}
