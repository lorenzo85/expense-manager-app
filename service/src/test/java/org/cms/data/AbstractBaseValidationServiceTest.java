package org.cms.data;

import org.cms.data.config.RepositoryConfig;
import org.cms.data.config.ServiceConfig;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(Parameterized.class)
@SpringApplicationConfiguration(classes = { ServiceConfig.class, RepositoryConfig.class })
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
public abstract class AbstractBaseValidationServiceTest {

    final TestContextManager testContextManager;

    public AbstractBaseValidationServiceTest() {
        this.testContextManager = new TestContextManager(getTestClass());
    }

    @Before
    public void injectDependencies() throws Throwable {
        this.testContextManager.prepareTestInstance(this);
    }

    protected abstract Class<?> getTestClass();
}
