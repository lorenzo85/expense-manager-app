package org.cms.core.commons;


public class PaymentStateDto {

    private String value;
    private String name;

    public PaymentStateDto(String value, String name) {
        this.name = name;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
