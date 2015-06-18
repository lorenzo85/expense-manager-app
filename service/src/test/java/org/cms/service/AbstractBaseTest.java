package org.cms.service;


import org.cms.service.expense.ExpenseRepository;
import org.cms.service.income.IncomeRepository;
import org.cms.service.user.UserRepository;
import org.cms.service.yard.YardRepository;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:test.properties")
@SpringApplicationConfiguration(classes = { ConfigurationService.class, ConfigurationRepository.class })
public abstract class AbstractBaseTest {

    @Autowired
    private UserRepository userRepo;
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
        userRepo.deleteAll();
    }
}