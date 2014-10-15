package com.spring.cms.persistence.domain;

public enum ExpenseCategory {

    OTHER(1, "Other"),
    CHECKS(2, "Checks"),
    SALARIES(3, "Salaries"),
    MORTGAGES(4, "Mortgages"),
    TRANSFERS(5, "Transfers");

    private int value;
    private String name;

    ExpenseCategory(int value, String name) {
        this.name = name;
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public static ExpenseCategory getFromValue(int value) {
        for(ExpenseCategory category : ExpenseCategory.values()) {
            if(category.getValue() == value) {
                return category;
            }
        }
        throw new IllegalArgumentException("The given value does not match any expense category!");
    }
}
