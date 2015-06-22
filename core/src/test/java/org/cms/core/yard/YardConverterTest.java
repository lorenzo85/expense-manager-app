package org.cms.core.yard;

import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static java.util.Collections.singletonList;
import static org.cms.core.ConfigurationService.DOZER_MAPPER_SPEC;
import static org.joda.money.CurrencyUnit.GBP;
import static org.joda.money.Money.of;
import static org.junit.Assert.assertEquals;

public class YardConverterTest {

    DozerBeanMapper mapper;
    Random random;

    @Before
    public void setUp() {
        mapper = new DozerBeanMapper();
        mapper.setMappingFiles(singletonList(DOZER_MAPPER_SPEC));
        random = new Random();
    }

    @Test
    public void shouldMapCorrectlyToYardDto() {
        // Given
        Yard yard = new Yard();
        yard.id = random.nextLong();
        yard.name = "A yard name";
        yard.description = "A yard description";
        yard.contractTotalAmount = of(GBP, 234.43);

        // When
        YardDto dto = mapper.map(yard, YardDto.class);

        // Then
        assertEquals(yard.id, dto.getId().longValue());
        assertEquals(yard.name, dto.getName());
        assertEquals(yard.description, dto.getDescription());
        assertEquals(yard.contractTotalAmount, dto.getContractTotalAmount());
    }

    @Test
    public void shouldMapCorrectlyToYard() {
        // Given
        YardDto dto = new YardDto();
        dto.setId(random.nextLong());
        dto.setName("A yard name");
        dto.setDescription("A yard description");
        dto.setContractTotalAmount(of(GBP, 23.34));

        // When
        Yard yard = mapper.map(dto, Yard.class);

        // Then
        assertEquals(dto.getId().longValue(), yard.id);
        assertEquals(dto.getName(), yard.name);
        assertEquals(dto.getDescription(), yard.description);
        assertEquals(dto.getContractTotalAmount(), yard.contractTotalAmount);
    }
}
