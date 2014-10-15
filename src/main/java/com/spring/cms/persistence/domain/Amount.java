package com.spring.cms.persistence.domain;

import java.math.BigDecimal;

public interface Amount {

    BigDecimal getAmount();

    PaymentState getStatus();
}
