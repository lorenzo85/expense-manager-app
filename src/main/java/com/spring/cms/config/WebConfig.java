package com.spring.cms.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.spring.cms.service.serializers.JodaMoneyDeserializer;
import com.spring.cms.service.serializers.JodaMoneySerializer;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan(basePackages = {"com.spring.cms.web.controller"})
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public Module jodaMoneyModule(CurrencyUnit currency) {
        return new JodaMoneyModule(currency);
    }

    private static class JodaMoneyModule extends SimpleModule {
        public JodaMoneyModule(CurrencyUnit currency) {
            addDeserializer(Money.class, new JodaMoneyDeserializer(currency));
            addSerializer(Money.class, new JodaMoneySerializer());
        }
    }
}