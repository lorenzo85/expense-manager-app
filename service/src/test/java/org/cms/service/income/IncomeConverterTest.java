package org.cms.service.income;

import org.cms.service.commons.PaymentState;
import org.cms.service.yard.Yard;
import org.dozer.DozerBeanMapper;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static java.util.Collections.singletonList;
import static org.cms.service.ConfigurationService.DOZER_MAPPER_SPEC;
import static org.joda.money.CurrencyUnit.AUD;
import static org.joda.money.Money.of;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.util.ReflectionTestUtils.getField;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class IncomeConverterTest {

    DozerBeanMapper mapper;
    Random random;

    @Before
    public void setUp() {
        mapper = new DozerBeanMapper();
        mapper.setMappingFiles(singletonList(DOZER_MAPPER_SPEC));
        random = new Random();
    }

    @Test
    public void shouldMapCorrectlyToIncomeDto() {
        // Given
        Yard yard = new Yard();
        setField(yard, "id", random.nextLong());
        Income income = new Income();
        income.id = random.nextLong();
        income.yard = yard;
        income.invoiceId = random.nextLong();
        income.amount = of(AUD, 23.43);
        income.status = PaymentState.PAID;
        income.note = "A note";

        // When
        IncomeDto dto = mapper.map(income, IncomeDto.class);

        // Then
        assertEquals(income.id, dto.getId().longValue());
        assertEquals(getField(income.yard, "id"), dto.getYardId());
        assertEquals(income.invoiceId, dto.getInvoiceId().longValue());
        assertEquals(income.amount, dto.getAmount());
        assertEquals(income.status, dto.getStatus());
        assertEquals(income.note, dto.getNote());
    }

    @Test
    public void shouldMapCorrectlyToIncome() {
        // Given
        IncomeDto dto = new IncomeDto();
        dto.setId(random.nextLong());
        dto.setYardId(random.nextLong());
        dto.setInvoiceId(random.nextLong());
        dto.setAmount(Money.of(CurrencyUnit.CHF, 234.42));
        dto.setStatus(PaymentState.UNPAID);
        dto.setNote("A note");

        // When
        Income income = mapper.map(dto, Income.class);

        // Then
        assertEquals(dto.getId().longValue(), income.id);
        assertEquals(dto.getYardId(), getField(income.yard, "id"));
        assertEquals(dto.getInvoiceId().longValue(), income.invoiceId);
        assertEquals(dto.getAmount(), income.amount);
        assertEquals(dto.getStatus(), income.status);
        assertEquals(dto.getNote(), income.note);
    }
}
