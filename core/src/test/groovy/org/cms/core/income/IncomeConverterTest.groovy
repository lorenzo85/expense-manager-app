package org.cms.core.income
import org.cms.core.BaseSpecification
import org.cms.core.yard.Yard
import org.dozer.DozerBeanMapper

import static java.util.Collections.singletonList
import static org.cms.core.ConfigurationService.DOZER_MAPPER_SPEC
import static org.cms.core.commons.PaymentState.PAID
import static org.cms.core.commons.PaymentState.UNPAID
import static org.springframework.test.util.ReflectionTestUtils.getField
import static org.springframework.test.util.ReflectionTestUtils.setField

class IncomeConverterTest extends BaseSpecification {


    def mapper = new DozerBeanMapper();
    def random = new Random();

    void setup() {
        mapper.setMappingFiles(singletonList(DOZER_MAPPER_SPEC));
    }

    def "Should map correctly to dto"() {
        given:
        def yard = new Yard()
        setField(yard, "id", random.nextLong())
        def income = new Income()
        income.yard = yard;
        income.status = PAID;
        income.note = "A note";
        income.id = random.nextLong();
        income.amount = amountOf(234.23)
        income.invoiceId = random.nextLong();

        when:
        def dto = mapper.map(income, IncomeDto.class)

        then:
        income.id == dto.id.longValue()
        getField(income.yard, "id") == dto.yardId
        income.invoiceId == dto.invoiceId.longValue()
        income.amount == dto.amount
        income.status == dto.status
        income.note == dto.note
    }

    def "Should map correctly to income"() {
        given:
        def dto = IncomeDto.builder()
                .id(random.nextLong())
                .yardId(random.nextLong())
                .invoiceId(random.nextLong())
                .amount(amountOf(234))
                .status(UNPAID)
                .note("A note")
                .build()

        when:
        def income = mapper.map(dto, Income.class)

        then:
        income.id == dto.id.longValue()
        getField(income.yard, "id") == dto.yardId
        income.invoiceId == dto.invoiceId.longValue()
        income.amount == dto.amount
        income.status == dto.status
        income.note == dto.note
    }

}
