package com.spring.cms;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static org.springframework.boot.SpringApplication.run;

@ComponentScan(basePackages = "com.spring.cms.config")
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@EnableTransactionManagement
public class Application {

        public static void main(String[] args) {
            run(Application.class, args);
        }

}