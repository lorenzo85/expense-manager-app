package org.cms.data.domain;

public enum PaymentState {

    PAID(1, "Paid"), UNPAID(2, "Unpaid");

    private int value;
    private String name;

    PaymentState(int value, String name) {
        this.name = name;
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public static PaymentState getFromValue(int value) {
        for(PaymentState state : PaymentState.values()) {
            if(state.getValue() == value) {
                return state;
            }
        }
        throw new IllegalArgumentException("The given value does not match any payment state!");
    }
}