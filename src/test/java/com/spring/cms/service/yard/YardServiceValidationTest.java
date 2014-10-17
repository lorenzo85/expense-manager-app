package com.spring.cms.service.yard;

import com.spring.cms.config.PersistenceConfig;
import com.spring.cms.config.ServiceConfig;
import com.spring.cms.service.dto.YardDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.Arrays;
import java.util.Collection;

import static org.joda.money.CurrencyUnit.USD;
import static org.joda.money.Money.of;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
@ContextConfiguration(classes = {PersistenceConfig.class, ServiceConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public class YardServiceValidationTest {

    @Autowired
    YardService yardService;

    YardDto dto;
    Class expected;
    final TestContextManager testContextManager;

    public YardServiceValidationTest(YardDto dto, Class expected) {
        this.dto = dto;
        this.expected = expected;
        this.testContextManager = new TestContextManager(getClass());
    }

    @Before
    public void injectDependencies() throws Throwable {
        this.testContextManager.prepareTestInstance(this);
    }

    @Parameterized.Parameters
    public static Collection dtos() {
        return Arrays.asList(new Object[][] {
                { new YardDto(null, "A description", of(USD, 1500.50)), IllegalArgumentException.class },
                { new YardDto("A name", null, of(USD, 2300.20)), IllegalArgumentException.class },
                { new YardDto("A name", "A description", null), IllegalArgumentException.class}
        });
    }

    @Test
    public void thatIllegalArgumentExceptionIsThrownForInvalidDto() {
        // Expect
        try {
            yardService.save(this.dto);
            fail("Should have thrown=" + this.expected + " exception.");
        } catch (Exception e) {
            assertEquals(expected, e.getClass());
        }
    }
}
