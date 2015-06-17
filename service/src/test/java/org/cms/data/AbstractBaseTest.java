package org.cms.data;


import org.cms.data.config.RepositoryConfig;
import org.cms.data.config.ServiceConfig;
import org.cms.data.repository.ExpenseRepository;
import org.cms.data.repository.IncomeRepository;
import org.cms.data.repository.YardRepository;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {ServiceConfig.class, RepositoryConfig.class})
public abstract class AbstractBaseTest {

    @Autowired
    protected YardRepository yardRepo;
    @Autowired
    private IncomeRepository incomeRepo;
    @Autowired
    private ExpenseRepository expenseRepo;

    @After
    public void teardown() {
        expenseRepo.deleteAll();
        incomeRepo.deleteAll();
        yardRepo.deleteAll();
    }
}