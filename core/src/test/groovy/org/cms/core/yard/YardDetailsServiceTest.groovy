package org.cms.core.yard
import org.cms.core.BaseSpecification

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals
import static org.cms.core.commons.PaymentState.PAID
import static org.cms.core.commons.PaymentState.UNPAID
import static org.cms.core.expense.ExpenseCategory.CHECKS
import static org.cms.core.expense.ExpenseCategory.MORTGAGES

class YardDetailsServiceTest extends BaseSpecification {

    def aYard = YardDto.builder()
            .name("A name")
            .description("A description")
            .contractTotalAmount(amountOf(23324.43))
            .build()

    def "Should have all collections empty"() {
        given:
        def yardId = yardService.save(aYard).id

        when:
        def extendedYard = yardService.getYardDetails(yardId)

        then:
        extendedYard.expenses.isEmpty()
        extendedYard.incomes.isEmpty()
    }

    def "Should have all amounts to zero"() {
        given:
        def yardId = yardService.save(aYard).id

        when:
        def extendedYard = yardService.getYardDetails(yardId)
        def summary = extendedYard.summary

        then:
        // When a new yard gets created, the delta missing income equals
        // the contract total amount if there are no incomes at all.
        summary.deltaMissingIncome == aYard.getContractTotalAmount()
        summary.deltaPaid == amountOf(0)
        summary.paidIncomes == amountOf(0)
        summary.paidExpenses == amountOf(0)
        summary.unPaidExpenses == amountOf(0)
    }

    def "Should have all expenses"() {
        given:
        def yardId = yardService.save(aYard).id
        def expense1 = saveExpense(yardId, 24324L, "Expense title 1", "Expense note 1", amountOf(23.43), DATE_FORMATTER.parse("12/01/2013"), DATE_FORMATTER.parse("1/1/2000"), PAID, MORTGAGES)
        def expense2 = saveExpense(yardId, 543L, "Expense title 2", "Expense note 2", amountOf(54.23), DATE_FORMATTER.parse("20/02/2013"), DATE_FORMATTER.parse("20/02/1999"), UNPAID, CHECKS)


        when:
        def persistedYard = yardService.getYardDetails(yardId)
        def expenses = persistedYard.expenses

        then:
        expenses.size() == 2
        contains(expenses, expense1)
        contains(expenses, expense2)
    }

    def "Should have all incomes"() {
        given:
        def yardId = yardService.save(aYard).id
        def income1 = saveIncome(yardId, 234L, UNPAID, amountOf(234.23), "A note 1")
        def income2 = saveIncome(yardId, 6434L, PAID, amountOf(4532.1), "A note 2")

        when:
        def persistedYard = yardService.getYardDetails(yardId)
        def incomes = persistedYard.incomes

        then:
        incomes.size() == 2
        contains(incomes, income1)
        contains(incomes, income2)
    }

    def "Should compute correctly total paid incomes"() {
        given:
        def yardId = yardService.save(aYard).id
        def amount1 = amountOf(22.12)
        def amount2 = amountOf(654.23)
        def amount3 = amountOf(765.34)
        saveIncome(yardId, 23L, PAID, amount1, "A note 1")
        saveIncome(yardId, 452L, UNPAID, amount2, "A note 2")
        saveIncome(yardId, 6534L, PAID, amount3, "A note 3")

        when:
        def persistedYard = yardService.getYardDetails(yardId)
        def summary = persistedYard.summary

        then:
        summary.paidIncomes == amount1.plus(amount3)
    }

    def "Should compute correctly total paid expenses"() {
        given:
        def yardId = yardService.save(aYard).id
        def amount1 = amountOf(23423.43)
        def amount2 = amountOf(33.23)
        def amount3 = amountOf(645.2)
        saveExpense(yardId, 24324L, "Expense title 1", "Expense note 1", amount1, DATE_FORMATTER.parse("12/01/2013"), DATE_FORMATTER.parse("1/1/2000"), PAID, MORTGAGES)
        saveExpense(yardId, 124L, "Expense title 1", "Expense note 1", amount2, DATE_FORMATTER.parse("12/01/2013"), DATE_FORMATTER.parse("1/1/2000"), UNPAID, CHECKS)
        saveExpense(yardId, 24324L, "Expense title 1", "Expense note 1", amount3, DATE_FORMATTER.parse("12/01/2013"), DATE_FORMATTER.parse("1/1/2000"), PAID, MORTGAGES)

        when:
        def persistedYard = yardService.getYardDetails(yardId)
        def summary = persistedYard.summary

        then:
        summary.paidExpenses == amount1.plus(amount3)
    }

    def "Should compute unpaid expenses correctly"() {
        given:
        def yardId = yardService.save(aYard).id
        def amount1 = amountOf(235.23)
        def amount2 = amountOf(345.2)
        def amount3 = amountOf(432.21)
        saveExpense(yardId, 24324L, "Expense title 1", "Expense note 1", amount1, DATE_FORMATTER.parse("12/01/2013"), DATE_FORMATTER.parse("1/1/2000"), PAID, MORTGAGES)
        saveExpense(yardId, 124L, "Expense title 1", "Expense note 1", amount2, DATE_FORMATTER.parse("12/01/2013"), DATE_FORMATTER.parse("1/1/2000"), UNPAID, CHECKS)
        saveExpense(yardId, 24324L, "Expense title 1", "Expense note 1", amount3, DATE_FORMATTER.parse("12/01/2013"), DATE_FORMATTER.parse("1/1/2000"), UNPAID, MORTGAGES)

        when:
        def persistedYard = yardService.getYardDetails(yardId)
        def summary = persistedYard.summary

        then:
        summary.unPaidExpenses == amount2.plus(amount3)
    }

    def "Should compute delta missing income correctly"() {
        given:
        def yardId = yardService.save(aYard).id
        def amount1 = amountOf(23.2)
        def amount2 = amountOf(235.23)
        def amount3 = amountOf(435.1)
        saveIncome(yardId, 23L, PAID, amount1, "A note 1")
        saveIncome(yardId, 452L, UNPAID, amount2, "A note 2")
        saveIncome(yardId, 6534L, PAID, amount3, "A note 3")

        when:
        def persistedYard = yardService.getYardDetails(yardId)
        def summary = persistedYard.summary
        def totalPaidIncomes = amount1.plus(amount3)

        then:
        summary.deltaMissingIncome == aYard.getContractTotalAmount().minus(totalPaidIncomes)
    }

    def "Should compute delta paid correctly"() {
        given:
        def yardId = yardService.save(aYard).id
        def amount1 = amountOf(21.2)
        def amount2 = amountOf(1.2)
        def amount3 = amountOf(5.2)
        def amount4 = amountOf(245.23)
        def amount5 = amountOf(45.2)
        saveExpense(yardId, 24324L, "Expense title 1", "Expense note 1", amount1, DATE_FORMATTER.parse("12/01/2013"), DATE_FORMATTER.parse("1/1/2000"), PAID, MORTGAGES)
        saveExpense(yardId, 124L, "Expense title 1", "Expense note 1", amount2, DATE_FORMATTER.parse("12/01/2013"), DATE_FORMATTER.parse("1/1/2000"), UNPAID, CHECKS)
        saveIncome(yardId, 23L, PAID, amount3, "A note 1")
        saveIncome(yardId, 452L, UNPAID, amount4, "A note 2")
        saveIncome(yardId, 6534L, PAID, amount5, "A note 3")

        when:
        def persistedYard = yardService.getYardDetails(yardId)
        def summary = persistedYard.summary
        def totalPaidExpenses = amount1
        def totalPaidIncomes = amount3.plus(amount5)

        then:
        summary.deltaPaid == totalPaidIncomes.minus(totalPaidExpenses)
    }

    def <T> boolean contains(Collection<T> entities, T entity) {
        for (T e : entities) {
            if(reflectionEquals(entity, e)) {
                return true
            }
        }
        return false
    }
}
