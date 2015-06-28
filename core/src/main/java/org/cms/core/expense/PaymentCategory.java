package org.cms.core.expense;

public enum PaymentCategory {

    OTHER(1, "Other"),
    CHECKS(2, "Checks"),
    SALARIES(3, "Salaries"),
    MORTGAGES(4, "Mortgages"),
    TRANSFERS(5, "Transfers");

    private int value;
    private String name;

    PaymentCategory(int value, String name) {
        this.name = name;
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public static PaymentCategory getFromValue(int value) {
        for(PaymentCategory category : PaymentCategory.values()) {
            if(category.getValue() == value) {
                return category;
            }
        }
        throw new IllegalArgumentException("The given value does not match any expense category!");
    }
}
