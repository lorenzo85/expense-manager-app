package org.cms.core.expense

import org.cms.core.BaseSpecification
import org.cms.core.commons.EntityNotFoundException
import org.springframework.dao.DataIntegrityViolationException

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals
import static org.cms.core.commons.PaymentState.PAID
import static org.cms.core.commons.PaymentState.UNPAID
import static org.cms.core.expense.ExpenseCategory.CHECKS

class ExpenseServiceTest extends BaseSpecification {

    def expiresAt = DATE_FORMATTER.parse("01/12/2014")
    def emissionAt = DATE_FORMATTER.parse("20/3/2015")
    def yardDto
    def expenseDto
    def expenseDtoTemplate

    void setup() {
        yardDto = saveYard("Yard 1", "Yard 1 description", amountOf(23.3))
        expenseDto = saveExpense(yardDto.getId(), 3459L, "A title", "A note", amountOf(765.34), expiresAt, emissionAt, PAID, CHECKS);
        expenseDtoTemplate = ExpenseDto.builder()
                .yardId(yardDto.getId()).invoiceId(234L).title("A title")
                .note("A note").amount(amountOf(23.3)).expiresAt(expiresAt)
                .emissionAt(emissionAt).status(PAID).category(CHECKS).build()
    }

    def "Should assign an id to persisted expense"() {
        when:
        def persisted = expenseService.save(expenseDtoTemplate)
        def found = expenseService.findOne(persisted.id)

        then:
        found.id != 0
    }

    def "Should throw exception if yard id does not exists when save"() {
        given:
        def unexistentId = -1
        expenseDtoTemplate.yardId = unexistentId

        when:
        expenseService.save(expenseDtoTemplate)

        then:
        thrown(DataIntegrityViolationException)
    }

    def "Should throw exception if yard id does not exists when update"() {
        given:
        def unexistentId = -1
        expenseDto.yardId = unexistentId

        when:
        expenseService.update(expenseDto)

        then:
        thrown(Exception)
    }

    def "Should throw exception when amount is too big"() {
        given:
        def amount = amountOf(123456789123456789232323.23)
        expenseDtoTemplate.amount = amount

        when:
        expenseService.save(expenseDtoTemplate)

        then:
        thrown(DataIntegrityViolationException)
    }

    def "Save expense does not throw exception when amount value is at its limit"() {
        given:
        def amount = amountOf(9999999999.99)
        expenseDtoTemplate.amount = amount

        when:
        def persisted = expenseService.save(expenseDtoTemplate)
        def dto = expenseService.findOne(persisted.id)

        then:
        dto.amount == amount
    }

    def "Should set note to null when not provided"() {
        given:
        expenseDtoTemplate.note = null

        when:
        def persisted = expenseService.save(expenseDtoTemplate)
        def actual = expenseService.findOne(persisted.id)

        then:
        actual.note == null
    }

    def "Should properly update expense"() {
        given: "We update data for already persisted expense"
        expenseDto.status = UNPAID
        expenseDto.note = "A modified note"
        expenseDto.title = "A new title"
        expenseDto.invoiceId = 25235L
        expenseDto.amount = amountOf(111111.1)
        expenseDto.expiresAt = DATE_FORMATTER.parse("6/1/1980")

        when: "We update it"
        expenseService.update(expenseDto)
        def persisted = expenseService.findOne(expenseDto.id)

        then: "Data should be correctly updated"
        reflectionEquals(expenseDto, persisted)
    }

    def "Should update expense without creating new expense"() {
        given:
        def oldCount = expenseService.count()

        when:
        expenseService.update(expenseDto)
        def newCount = expenseService.count()

        then:
        oldCount == newCount
    }

    def "Should throw exception when update expense for invalid id"() {
        given:
        expenseDto.id = -1

        when:
        expenseService.update(expenseDto)

        then:
        thrown(EntityNotFoundException)
    }

    def "Should correctly delete expense"() {
        given:
        expenseService.delete(expenseDto.id)

        when:
        expenseService.findOne(expenseDto.id)

        then:
        thrown(EntityNotFoundException)
    }

}
