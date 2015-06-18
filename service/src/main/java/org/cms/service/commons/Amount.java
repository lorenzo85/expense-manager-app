package org.cms.service.commons;

import org.joda.money.Money;

public interface Amount {

    Money getAmount();

    PaymentState getStatus();
}
