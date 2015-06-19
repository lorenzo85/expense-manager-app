package org.cms.service.commons;

import org.joda.money.Money;

import java.sql.Timestamp;

public interface Payment {

    Money getAmount();

    PaymentState getStatus();

    Timestamp getExpiresAt();

}
