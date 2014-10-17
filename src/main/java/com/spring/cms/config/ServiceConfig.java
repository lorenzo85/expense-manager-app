package com.spring.cms.config;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;

import static javax.validation.Validation.buildDefaultValidatorFactory;

@Configuration
@ComponentScan(basePackages = {"com.spring.cms.service"})
public class ServiceConfig {

    public static final String DOZER_MAPPING = "mapping.xml";

    @Bean
    @Scope("prototype")
    public Validator getValidator() {
        ValidatorFactory factory = buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    @Bean
    @Scope("singleton")
    public Mapper getMapper() {
        List<String> mappers = new ArrayList<>();
        mappers.add(DOZER_MAPPING);

        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.setMappingFiles(mappers);
        return mapper;
    }

    @Bean
    @Scope("prototype")
    public Money getMoney(CurrencyUnit currencyUnit) {
        return Money.of(currencyUnit, 0);
    }

    @Bean
    public CurrencyUnit getCurrencyUnit() {
        return CurrencyUnit.EUR;
    }
}