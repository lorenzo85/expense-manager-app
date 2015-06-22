package org.cms.core.yard

import org.cms.core.BaseSpecification
import org.cms.core.commons.EntityNotFoundException
import org.springframework.beans.factory.annotation.Autowired

public class YardServiceTest extends BaseSpecification {

    @Autowired YardService service
    @Autowired YardRepository repository

    def aYard = YardDto.builder()
            .name("A name")
            .description("A description")
            .contractTotalAmount(amountOf(23.43))
            .build()

    def yard1 = YardDto.builder().name("A name 1").description("A description 1")
            .contractTotalAmount(amountOf(12.32)).build()
    def yard2 = YardDto.builder().name("A name 2").description("A description 2")
            .contractTotalAmount(amountOf(43.43)).build()

    void cleanup() {
        repository.deleteAll()
    }

    def "Should assign an id when yard is saved"() {
        when:
        aYard = service.save(aYard)

        then:
        aYard.id != 0
        aYard.id != null
    }

    def "Should find persisted yard by id"() {
        given:
        aYard = service.save(aYard)

        when:
        def foundYard = service.findOne(aYard.id)

        then:
        foundYard != null
    }

    def "Should throw exception when find with unexistent id"() {
        given:
        def unexistentId = -1L

        when:
        service.findOne(unexistentId)

        then:
        thrown(EntityNotFoundException)
    }

    def "Should persist all yard parameters correctly"() {
        when:
        def yardId = service.save(aYard).id

        then:
        def persistedYard = service.findOne(yardId)
        matchesYard(aYard, persistedYard)
    }

    def "Should correctly update yard"() {
        given:
        def persistedYard = service.save(aYard)
        def newName = "A new name"

        when:
        persistedYard.name = newName
        service.update persistedYard

        then:
        def updatedYard = service.findOne persistedYard.id
        updatedYard.name == newName
    }

    def "Should correctly delete yard"() {
        given:
        def persistedYard = service.save(aYard)

        when:
        service.delete(persistedYard.id)
        service.findOne(persistedYard.id)

        then:
        thrown(EntityNotFoundException)
    }

    def "Should find all yards"() {
        given:
        service.save(yard1)
        service.save(yard2)

        when:
        def yards = service.findAll()

        then:
        yards.size == 2
    }

    def "Should count correctly"() {
        given:
        service.save(yard1)
        service.save(yard2)

        when:
        def count = service.count()

        then:
        count == 2L
    }

    def matchesYard(expected, given) {
        expected.name = given.name
        expected.description = given.description
        expected.contractTotalAmount = given.contractTotalAmount
    }

}
