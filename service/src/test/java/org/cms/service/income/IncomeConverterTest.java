package org.cms.service.income;

import org.cms.service.yard.Yard;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static java.util.Collections.singletonList;
import static org.cms.service.ConfigurationService.DOZER_MAPPER_SPEC;

public class IncomeConverterTest {

    DozerBeanMapper mapper;
    Random random;

    @Before
    public void setUp() {
        mapper = new DozerBeanMapper();
        mapper.setMappingFiles(singletonList(DOZER_MAPPER_SPEC));
        random = new Random();
    }

    @Test
    public void shouldMapCorrectlyToIncomeDto() {
        // Given
        Yard yard = new Yard();

    }
}
