package com.spring.cms.persistence.domain;

import org.joda.money.Money;

public interface Amount {

    Money getAmount();

    PaymentState getStatus();
}
