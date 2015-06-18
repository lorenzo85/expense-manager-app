package org.cms.service.expense;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static org.cms.service.expense.ExpenseCategory.getFromValue;

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
