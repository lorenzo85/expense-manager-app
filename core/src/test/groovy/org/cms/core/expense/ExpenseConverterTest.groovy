package org.cms.core.expense
import org.cms.core.BaseSpecification
import org.cms.core.yard.Yard
import org.dozer.DozerBeanMapper

import java.sql.Timestamp

import static java.lang.System.currentTimeMillis
import static java.util.Collections.singletonList
import static org.cms.core.ConfigurationService.DOZER_MAPPER_SPEC
import static org.cms.core.commons.PaymentState.PAID
import static org.cms.core.commons.PaymentState.UNPAID
import static PaymentCategory.CHECKS
import static PaymentCategory.MORTGAGES
import static org.springframework.test.util.ReflectionTestUtils.getField
import static org.springframework.test.util.ReflectionTestUtils.setField

class ExpenseConverterTest extends BaseSpecification {

    def mapper = new DozerBeanMapper();
    def random = new Random();

    void setup() {
        mapper.setMappingFiles(singletonList(DOZER_MAPPER_SPEC))
    }

    def "Should map correctly to dto"() {
        given:
        Yard yard = new Yard()
        setField(yard, "id", random.nextLong())

        Expense expense = new Expense()
        expense.yard = yard
        expense.status = UNPAID
        expense.category = MORTGAGES
        expense.id = random.nextLong()
        expense.amount = amountOf(23.43)
        expense.note = "An expense note"
        expense.title = "An expense title"
        expense.invoiceId = random.nextLong()
        expense.expiresAt = new Timestamp(currentTimeMillis())
        expense.emissionAt = new Timestamp(currentTimeMillis() +  20)

        when:
        def dto = mapper.map(expense, ExpenseDto.class)

        then:
        getField(expense.yard, "id") == dto.getYardId()
        expense.status == dto.getStatus()
        expense.category == dto.getCategory()
        expense.id == dto.getId().longValue()
        expense.amount == dto.getAmount()
        expense.note == dto.getNote()
        expense.title == dto.getTitle()
        expense.invoiceId == dto.getInvoiceId()
        expense.expiresAt.compareTo(dto.getExpiresAt()) == 0
        expense.emissionAt.compareTo(dto.getEmissionAt()) == 0
    }

    def "Should map correctly to Expense"() {
        given:
        def dto = ExpenseDto.builder()
                .yardId(random.nextLong())
                .status(PAID)
                .category(CHECKS)
                .id(random.nextLong())
                .amount(amountOf(23.23))
                .note("A note")
                .title("A title")
                .invoiceId(random.nextLong())
                .expiresAt(new Date(currentTimeMillis() + 10))
                .emissionAt(new Date(currentTimeMillis() + 40))
                .build()

        when:
        def expense = mapper.map(dto, Expense.class)

        then:
        getField(expense.yard, "id") == dto.getYardId().longValue()
        expense.id == dto.getId()
        expense.status == dto.getStatus()
        expense.category == dto.getCategory()
        expense.amount == dto.getAmount()
        expense.note == dto.getNote()
        expense.title == dto.getTitle()
        expense.invoiceId == dto.getInvoiceId().longValue()
        expense.expiresAt.compareTo(dto.getExpiresAt()) == 0
        expense.emissionAt.compareTo(dto.getEmissionAt()) == 0
    }

}
