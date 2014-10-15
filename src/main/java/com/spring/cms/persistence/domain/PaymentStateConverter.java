package com.spring.cms.persistence.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static com.spring.cms.persistence.domain.PaymentState.*;

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
