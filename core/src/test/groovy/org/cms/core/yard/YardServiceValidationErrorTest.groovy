package org.cms.core.yard

import spock.lang.Unroll

import static org.cms.core.yard.YardDto.*

class YardServiceValidationErrorTest extends YardBaseSpecification {

    @Unroll
    def "Should throw exception when saving invalid dto [#dto]"() {
        when: "Try to save an invalid dto"
        service.save(dto)

        then:
        thrown(IllegalArgumentException)

        where:
        dto << [
                builder().name(null).description("A description").contractTotalAmount(amountOf(23324.43)).build(),
                builder().name("").description("A description").contractTotalAmount(amountOf(234.2)).build(),
                builder().name("A name").description(null).contractTotalAmount(amountOf(23324.43)).build(),
                builder().name("A name").description("A description").contractTotalAmount(null).build(),
                builder().name("a".multiply(201)).description("A description").contractTotalAmount(amountOf(23.3)).build()
        ]
    }

    @Unroll
    def "Should throw exception when updating invalid dto [#dto]"() {
        when: "Try to update an invalid dto"
        service.update(dto)

        then:
        thrown(IllegalArgumentException)

        where:
        dto << [
                builder().name(null).description("A description").contractTotalAmount(amountOf(23324.43)).build(),
                builder().name("").description("A description").contractTotalAmount(amountOf(234.2)).build(),
                builder().name("A name").description(null).contractTotalAmount(amountOf(23324.43)).build(),
                builder().name("A name").description("A description").contractTotalAmount(null).build(),
                builder().name("a".multiply(201)).description("A description").contractTotalAmount(amountOf(23.3)).build()
        ]
    }

}
