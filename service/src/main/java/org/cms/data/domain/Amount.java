package org.cms.data.domain;

import org.joda.money.Money;

public interface Amount {

    Money getAmount();

    PaymentState getStatus();
}
