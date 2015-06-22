package org.cms.core.yard

import org.cms.core.commons.PaymentCollectionMathBuilder
import org.cms.core.expense.Expense
import spock.lang.Specification

import static java.util.Collections.singletonList
import static org.joda.money.CurrencyUnit.EUR


class PaymentCollectionMathBuilderTest extends Specification {

    def "Should throw exception when payment status not specified"() {
        given:
        def expenses = singletonList(new Expense());

        when:
        PaymentCollectionMathBuilder.sum().on(expenses).compute(EUR);

        then:
        thrown(IllegalArgumentException)
    }
}
