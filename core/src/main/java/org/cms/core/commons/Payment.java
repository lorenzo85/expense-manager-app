package org.cms.core.commons;

import org.cms.core.expense.PaymentCategory;
import org.joda.money.Money;

import java.sql.Timestamp;

public interface Payment {

    Money getAmount();

    PaymentState getStatus();

    Timestamp getExpiresAt();

    PaymentCategory getCategory();
}
