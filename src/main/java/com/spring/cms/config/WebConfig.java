package com.spring.cms.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan(basePackages = {"com.spring.cms.web.controller"})
public class WebConfig extends WebMvcConfigurerAdapter {

}