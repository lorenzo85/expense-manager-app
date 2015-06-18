package org.cms.rest.config.web;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@ComponentScan({"org.cms.rest"})
public class ConfigurationWeb extends WebMvcConfigurerAdapter {

    @Bean
    public Module jodaMoneyModule() {
        return new JodaMoneyModule();
    }

    private static class JodaMoneyModule extends SimpleModule {
        public JodaMoneyModule() {
            addDeserializer(Money.class, new JodaMoneyDeserializer(CurrencyUnit.EUR));
            addSerializer(Money.class, new JodaMoneySerializer());
        }
    }
}