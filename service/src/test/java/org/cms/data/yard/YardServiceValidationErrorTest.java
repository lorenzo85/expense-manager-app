package org.cms.data.yard;


import org.cms.data.AbstractBaseValidationServiceTest;
import org.cms.data.dto.YardDto;
import org.cms.data.service.YardService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.joda.money.CurrencyUnit.USD;
import static org.joda.money.Money.of;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@Ignore
public class YardServiceValidationErrorTest extends AbstractBaseValidationServiceTest {

    @Autowired
    YardService yardService;

    YardDto dto;
    Class expectedException;

    public YardServiceValidationErrorTest(YardDto dto, Class expectedException) {
        super();
        this.dto = dto;
        this.expectedException = expectedException;
    }

    @Parameterized.Parameters
    public static Collection dtos() {
        return asList(new Object[][] {
                { new YardDto(null, "A description", of(USD, 1500.50)), IllegalArgumentException.class },
                { new YardDto("", "A description", of(USD, 1250)), IllegalArgumentException.class },
                { new YardDto("A name", null, of(USD, 2300.20)), IllegalArgumentException.class },
                { new YardDto("A name", "A description", null), IllegalArgumentException.class },
                { new YardDto("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "A description", of(USD, 23)), IllegalArgumentException.class }
        });
    }

    @Test
    public void thatIllegalArgumentExceptionIsThrownWhenSaveInvalidDto() {
        // Expect
        try {
            yardService.save(this.dto);
            fail("Should have thrown=" + this.expectedException + " exception.");
        } catch (Exception e) {
            assertEquals(expectedException, e.getClass());
        }
    }

    @Test
    public void thatIllegalArgumentExceptionIsThrownWhenUpdateInvalidDto() {
        // Expect
        try {
            yardService.update(this.dto);
            fail("Should have thrown=" + this.expectedException + " exception.");
        } catch (Exception e) {
            assertEquals(expectedException, e.getClass());
        }
    }

    @Override
    protected Class<?> getTestClass() {
        return getClass();
    }
}