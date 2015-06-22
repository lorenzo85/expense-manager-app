package org.cms.core.expense;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseCategoryDto {

    private String value;
    private String name;

    public ExpenseCategoryDto(String value, String name) {
        this.value = value;
        this.name = name;
    }
}
