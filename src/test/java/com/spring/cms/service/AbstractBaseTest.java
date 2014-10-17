package com.spring.cms.service;


import com.spring.cms.config.PersistenceConfig;
import com.spring.cms.config.ServiceConfig;
import com.spring.cms.persistence.repository.ExpenseRepository;
import com.spring.cms.persistence.repository.IncomeRepository;
import com.spring.cms.persistence.repository.YardRepository;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfig.class, ServiceConfig.class})
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