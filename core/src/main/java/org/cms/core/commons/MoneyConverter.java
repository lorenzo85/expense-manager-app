package org.cms.core.commons;

import org.dozer.CustomConverter;
import org.joda.money.Money;

public class MoneyConverter implements CustomConverter {
    @Override
    public Object convert(Object destination, Object source, Class<?> destClass, Class<?> sourceClass) {
        return Money.of((Money) source);
    }
}
