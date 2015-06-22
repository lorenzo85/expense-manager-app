package org.cms.core.yard;

import org.cms.core.AbstractBaseServiceTest;
import org.joda.money.Money;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import static java.lang.String.*;
import static org.junit.Assert.assertEquals;

public class YardPagingTest extends AbstractBaseServiceTest {

    Random random;

    @Before
    public void setUp() {
        random = new Random();
        createRandomYards(100);
    }

    @Test
    public void testFirstPage() {
        // Given
        int page = 0;
        int size = 10;

        // When
        List<YardDto> yards = yardService.findAll(page, size);

        // Then
        assertEquals(size, yards.size());

        YardDto firstYard = yards.get(0);
        YardDto lastYard = yards.get(yards.size() - 1);
        assertEquals(1, Integer.valueOf(firstYard.getName()).intValue());
        assertEquals(10, Integer.valueOf(lastYard.getName()).intValue());
    }

    @Test
    public void testSecondPage() {
        // Given
        int page = 1;
        int size = 7;

        // When
        List<YardDto> yards = yardService.findAll(page, size);

        // Then
        assertEquals(size, yards.size());

        YardDto firstYard = yards.get(0);
        YardDto lastYard = yards.get(yards.size() - 1);
        assertEquals(8, Integer.valueOf(firstYard.getName()).intValue());
        assertEquals(14, Integer.valueOf(lastYard.getName()).intValue());
    }

    private void createRandomYards(int number) {
        for (int i = 1; i <= number; i++) {
            String name = format("%d", i);
            String description = format("Yard description %d", i);
            Money amount = createAmount(random.nextInt(100));

            YardDto dto = YardDto.builder()
                    .name(name)
                    .description(description)
                    .contractTotalAmount(amount)
                    .build();

            yardService.save(dto);
        }
    }
}
