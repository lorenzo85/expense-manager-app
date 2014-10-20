package com.spring.cms.service.yard;

import com.spring.cms.service.AbstractBaseServiceTest;
import com.spring.cms.service.dto.YardDto;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.joda.money.CurrencyUnit.EUR;
import static org.joda.money.Money.of;
import static org.junit.Assert.*;

public class YardServiceValidationSuccessTest extends AbstractBaseServiceTest {

    @Autowired
    YardService yardService;

    YardDto yardDto;

    @Before
    public void setup() {
        yardDto = new YardDto("A name", "A description", of(EUR, 234));
    }

    @Test
    public void thatNameAtItsLimitIsPersisted() {
        // Given
        String maxNameLength = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        yardDto.setName(maxNameLength);
        assertEquals(200, maxNameLength.length());

        // When
        YardDto persisted = yardService.save(yardDto);

        // Then
        YardDto actual = yardService.findOne(persisted.getId());
        assertEquals(maxNameLength, actual.getName());
    }

    @Test
    public void thatNameOfLengthOneIsPersisted() {
        // Given
        String name = "a";
        yardDto.setName(name);

        // When
        YardDto persisted = yardService.save(yardDto);

        // Then
        YardDto actual = yardService.findOne(persisted.getId());
        assertEquals(name, actual.getName());
    }
}