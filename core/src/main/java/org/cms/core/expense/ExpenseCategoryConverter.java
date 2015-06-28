package org.cms.core.expense;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static org.cms.core.expense.PaymentCategory.getFromValue;

@Converter(autoApply = true)
public class ExpenseCategoryConverter implements AttributeConverter<PaymentCategory, Integer> {
    @Override
    public Integer convertToDatabaseColumn(PaymentCategory paymentCategory) {
        return paymentCategory.getValue();
    }

    @Override
    public PaymentCategory convertToEntityAttribute(Integer integer) {
        return getFromValue(integer);
    }
}
