package com.spring.cms.persistence.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static com.spring.cms.persistence.domain.ExpenseCategory.getFromValue;

@Converter(autoApply = true)
public class ExpenseCategoryConverter implements AttributeConverter<ExpenseCategory, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ExpenseCategory expenseCategory) {
        return expenseCategory.getValue();
    }

    @Override
    public ExpenseCategory convertToEntityAttribute(Integer integer) {
        return getFromValue(integer);
    }
}
