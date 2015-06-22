package org.cms.core.yard
import org.dozer.DozerBeanMapper

import static java.util.Collections.singletonList
import static org.cms.core.ConfigurationService.DOZER_MAPPER_SPEC

class YardConverterTest extends YardBaseSpecification {

    def mapper = new DozerBeanMapper();
    def random = new Random();

    void setup() {
        mapper.setMappingFiles(singletonList(DOZER_MAPPER_SPEC));
        random = new Random();
    }

    def "Should map correctly to dto"() {
        given:
        def yard = new Yard()
        yard.id = random.nextLong()
        yard.name = "A yard name"
        yard.description = "A yard description"
        yard.contractTotalAmount = amountOf(234.23)

        when:
        def dto = mapper.map(yard, YardDto.class)

        then:
        yard.id == dto.id.longValue()
        yard.name == dto.name
        yard.description == dto.description
        yard.contractTotalAmount == dto.contractTotalAmount
    }

    def "Should map correctly to yard"() {
        given:
        def dto = YardDto.builder()
                .id(random.nextLong())
                .name("A yard name")
                .description("A yard description")
                .contractTotalAmount(amountOf(325.23))
                .build()

        when:
        def yard = mapper.map(dto, Yard.class)

        then:
        dto.name == yard.name
        dto.id.longValue() == yard.id
        dto.description == yard.description
        dto.contractTotalAmount == yard.contractTotalAmount
    }
}
