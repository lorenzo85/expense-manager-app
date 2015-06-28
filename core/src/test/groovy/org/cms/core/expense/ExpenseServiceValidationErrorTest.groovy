package org.cms.core.expense
import org.cms.core.BaseSpecification
import spock.lang.Unroll

import java.text.SimpleDateFormat

import static org.cms.core.commons.PaymentState.PAID
import static PaymentCategory.CHECKS
import static org.cms.core.expense.ExpenseDto.*

class ExpenseServiceValidationErrorTest extends BaseSpecification {

    def static emissionAt = new SimpleDateFormat("dd/MM/yyyy").parse("12/2/2011");
    def static expiresAt = new SimpleDateFormat("dd/MM/yyyy").parse("1/10/1998");


    @Unroll
    def "Should throw exception when saving invalid dto [#dto]"() {
        when: "Try to save an invalid dto"
        expenseService.save(dto)

        then: "Illegal Argument exception is thrown"
        thrown(IllegalArgumentException)

        where:
        dto << [
                builder().invoiceId(null).title("A title").amount(amountOf(560)).status(PAID).category(CHECKS).expiresAt(expiresAt).emissionAt(emissionAt).note("A note").build(),
                builder().invoiceId(-1L).title("A title").amount(amountOf(560)).status(PAID).category(CHECKS).expiresAt(expiresAt).emissionAt(emissionAt).note("A note").build(),
                builder().invoiceId(111111111111L).title("A title").amount(amountOf(560)).status(PAID).category(CHECKS).expiresAt(expiresAt).emissionAt(emissionAt).note("A note").build(),
                builder().invoiceId(234L).title(null).amount(amountOf(560)).status(PAID).category(CHECKS).expiresAt(expiresAt).emissionAt(emissionAt).note("A note").build(),
                builder().invoiceId(234L).title("").amount(amountOf(560)).status(PAID).category(CHECKS).expiresAt(expiresAt).emissionAt(emissionAt).note("A note").build(),
                builder().invoiceId(234L).title("a".multiply(201)).amount(amountOf(560)).status(PAID).category(CHECKS).expiresAt(expiresAt).emissionAt(emissionAt).note("A note").build(),
                builder().invoiceId(234L).title("A title").amount(amountOf(560)).status(null).category(CHECKS).expiresAt(expiresAt).emissionAt(emissionAt).note("A note").build(),
                builder().invoiceId(234L).title("A title").amount(amountOf(560)).status(PAID).category(null).expiresAt(expiresAt).emissionAt(emissionAt).note("A note").build(),
                builder().invoiceId(234L).title("A title").amount(amountOf(560)).status(PAID).category(CHECKS).expiresAt(null).emissionAt(emissionAt).note("A note").build(),
                builder().invoiceId(234L).title("A title").amount(amountOf(560)).status(PAID).category(CHECKS).expiresAt(expiresAt).emissionAt(null).note("A note").build()
        ]
    }

    @Unroll
    def "Should throw exception when updating invalid dto [#dto]"() {
        when: "Try to update an invalid dto"
        expenseService.update(dto)

        then: "Illegal Argument exception is thrown"
        thrown(IllegalArgumentException)

        where:
        dto << [
                builder().invoiceId(null).title("A title").amount(amountOf(560)).status(PAID).category(CHECKS).expiresAt(expiresAt).emissionAt(emissionAt).note("A note").build(),
                builder().invoiceId(-1L).title("A title").amount(amountOf(560)).status(PAID).category(CHECKS).expiresAt(expiresAt).emissionAt(emissionAt).note("A note").build(),
                builder().invoiceId(111111111111L).title("A title").amount(amountOf(560)).status(PAID).category(CHECKS).expiresAt(expiresAt).emissionAt(emissionAt).note("A note").build(),
                builder().invoiceId(234L).title(null).amount(amountOf(560)).status(PAID).category(CHECKS).expiresAt(expiresAt).emissionAt(emissionAt).note("A note").build(),
                builder().invoiceId(234L).title("").amount(amountOf(560)).status(PAID).category(CHECKS).expiresAt(expiresAt).emissionAt(emissionAt).note("A note").build(),
                builder().invoiceId(234L).title("a".multiply(201)).amount(amountOf(560)).status(PAID).category(CHECKS).expiresAt(expiresAt).emissionAt(emissionAt).note("A note").build(),
                builder().invoiceId(234L).title("A title").amount(amountOf(560)).status(null).category(CHECKS).expiresAt(expiresAt).emissionAt(emissionAt).note("A note").build(),
                builder().invoiceId(234L).title("A title").amount(amountOf(560)).status(PAID).category(null).expiresAt(expiresAt).emissionAt(emissionAt).note("A note").build(),
                builder().invoiceId(234L).title("A title").amount(amountOf(560)).status(PAID).category(CHECKS).expiresAt(null).emissionAt(emissionAt).note("A note").build(),
                builder().invoiceId(234L).title("A title").amount(amountOf(560)).status(PAID).category(CHECKS).expiresAt(expiresAt).emissionAt(null).note("A note").build()
        ]
    }
}
