package com.spring.cms.service.dto;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class YardDto {

    private long id;
    @NotNull
    private String description;
    @NotNull
    @Size(min=1, max = 200)
    private String name;
    @NotNull
    @Digits(integer=10, fraction=2)
    @Min(0)
    private BigDecimal contractTotalAmount;

    public YardDto() {}

    public YardDto(YardDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.contractTotalAmount = dto.getContractTotalAmount();
    }

    public YardDto(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getContractTotalAmount() {
        return contractTotalAmount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContractTotalAmount(BigDecimal contractTotalAmount) {
        this.contractTotalAmount = contractTotalAmount;
    }
}
