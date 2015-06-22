package org.cms.core.yard

import org.cms.core.BaseSpecification
import org.joda.money.Money

import static java.lang.Math.*
import static java.lang.String.format

class YardFindAllByPageTest extends BaseSpecification {

    def random = new Random()
    def numberOfRandomYards = 100

    def setup() {
        createRandomYards(numberOfRandomYards)
    }

    def "Should find all for first page"() {
        given:
        def page = 0
        def size = 10

        when:
        def yards = yardService.findAll(page, size)
        def firstYard = yards.get(0)
        def lastYard = yards.get(yards.size() - 1)

        then:
        yards.size() == size
        1 == yardNameAsInt(firstYard)
        10 == yardNameAsInt(lastYard)
    }

    def "Should find all for second page"() {
        given:
        def page = 1
        def size = 7

        when:
        def yards = yardService.findAll(page, size)
        def firstYard = yards.get(0)
        def lastYard = yards.get(yards.size() - 1)

        then:
        yards.size() == size
        8 == yardNameAsInt(firstYard)
        14 == yardNameAsInt(lastYard)
    }

    def "Should find all for last page"() {
        given:
        def size = 7
        def lastPage = floor(numberOfRandomYards / size).toInteger()

        when:
        def yards = yardService.findAll(lastPage, size)
        def firstYard = yards.get(0)
        def lastYard = yards.get(yards.size() - 1)
        def numberOfYardsInLastPage = numberOfRandomYards % size

        then:
        yards.size == numberOfYardsInLastPage
        lastPage * size + 1 == yardNameAsInt(firstYard)
        lastPage * size + numberOfYardsInLastPage == yardNameAsInt(lastYard)
    }

    def yardNameAsInt(yard) {
        Integer.valueOf(yard.getName()).intValue()
    }

    def createRandomYards(number) {
        for (int i = 1; i <= number; i++) {
            def name = format("%d", i);
            def description = format("Yard description %d", i);
            Money amount = amountOf(random.nextInt(100));
            yardService.save(YardDto.builder()
                    .name(name)
                    .description(description)
                    .contractTotalAmount(amount)
                    .build());
        }
    }
}
