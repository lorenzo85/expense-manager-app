package org.cms.core.commons;

import org.cms.core.expense.ExpenseCategory;
import org.dozer.CustomConverter;
import org.joda.money.Money;

import java.util.HashMap;

public class SumsForCategoryConverter implements CustomConverter {
    @SuppressWarnings("unchecked")
    @Override
    public Object convert(Object destination, Object source, Class<?> destClass, Class<?> sourceClass) {
        return ((HashMap<ExpenseCategory, Money>) source).clone();
    }
}
