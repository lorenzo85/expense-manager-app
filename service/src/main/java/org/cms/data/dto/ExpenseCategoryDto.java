package org.cms.data.dto;

public class ExpenseCategoryDto {

    private String value;
    private String name;

    public ExpenseCategoryDto(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
