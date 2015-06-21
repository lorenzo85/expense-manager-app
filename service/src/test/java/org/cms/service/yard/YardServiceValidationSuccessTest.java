package org.cms.service.yard;


import org.cms.service.AbstractBaseServiceTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class YardServiceValidationSuccessTest extends AbstractBaseServiceTest {

    @Autowired
    YardService yardService;

    YardDto yardDto;

    @Before
    public void setup() {
        yardDto = YardDto.builder()
                .name("A name")
                .description("A description")
                .contractTotalAmount(createAmount(234.43))
                .build();
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