package org.cms.service.commons;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static org.cms.service.commons.PaymentState.getFromValue;

@Converter(autoApply = true)
public class PaymentStateConverter implements AttributeConverter<PaymentState, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PaymentState expenseStatus) {
        return expenseStatus.getValue();
    }

    @Override
    public PaymentState convertToEntityAttribute(Integer integer) {
        return getFromValue(integer);
    }
}
