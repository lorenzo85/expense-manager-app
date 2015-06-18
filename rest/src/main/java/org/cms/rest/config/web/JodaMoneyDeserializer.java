package org.cms.rest.config.web;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.io.IOException;

import static java.lang.Double.valueOf;

public class JodaMoneyDeserializer extends JsonDeserializer<Money> {

    private CurrencyUnit currency;

    public JodaMoneyDeserializer(CurrencyUnit currency) {
        this.currency = currency;
    }

    @Override
    public Money deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String text = jsonParser.getText();
        return Money.of(currency, valueOf(text));
    }
}
