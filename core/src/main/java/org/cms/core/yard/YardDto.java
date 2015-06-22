package org.cms.core.yard;

import lombok.*;
import org.cms.core.commons.Dto;
import org.joda.money.Money;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YardDto implements Dto<Long> {

    private long id;
    @NotNull
    private String description;
    @NotNull
    @Size(min=1, max = 200)
    private String name;
    @NotNull
    private Money contractTotalAmount;


    @Override
    public Long getId() {
        return id;
    }

}
