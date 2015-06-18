package org.cms.service.yard;

import org.cms.service.commons.Dto;
import org.joda.money.Money;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class YardDto implements Dto<Long> {

    private long id;
    @NotNull
    private String description;
    @NotNull
    @Size(min=1, max = 200)
    private String name;
    @NotNull
    private Money contractTotalAmount;

    public YardDto() {
    }

    public YardDto(YardDto dto) {
        this(dto.getName(), dto.getDescription(), dto.getContractTotalAmount());
        this.id = dto.getId();
    }

    public YardDto(String name, String description, Money contractTotalAmount) {
        this.name = name;
        this.description = description;
        this.contractTotalAmount = contractTotalAmount;
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

    public Money getContractTotalAmount() {
        return contractTotalAmount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContractTotalAmount(Money contractTotalAmount) {
        this.contractTotalAmount = contractTotalAmount;
    }

    @Override
    public Long getIdentifier() {
        return id;
    }
}
