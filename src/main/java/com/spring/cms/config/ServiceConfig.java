package com.spring.cms.config;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
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

    @Bean
    @Scope("prototype")
    public Validator getValidator() {
        ValidatorFactory factory = buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    @Bean
    @Scope("singleton")
    public Mapper getMapper() {
        List<String> mappingConfig = new ArrayList<String>();
        mappingConfig.add("expense-mapping.xml");

        DozerBeanMapper mapper = new DozerBeanMapper();
        mapper.setMappingFiles(mappingConfig);
        return mapper;
    }
}