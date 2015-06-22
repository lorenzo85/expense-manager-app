package org.cms.core.income;

import org.cms.core.AbstractBaseServiceTest;
import org.cms.core.commons.EntityNotFoundException;
import org.cms.core.commons.PaymentState;
import org.cms.core.yard.YardDto;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class IncomeServiceTest extends AbstractBaseServiceTest {


    @Test
    public void testIncomeIsPersisted() {
        // Given
        IncomeDto persisted = persistIncome();

        // Then
        IncomeDto found = incomeService.findOne(persisted.getId());
        assertNotNull(found);
    }

    private IncomeDto persistIncome() {
        YardDto yardDto = persistYardDto("a name", "a contract", createAmount(234.43));
        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setYardId(yardDto.getId());
        incomeDto.setAmount(createAmount(765.23));
        incomeDto.setInvoiceId(2432L);
        incomeDto.setStatus(PaymentState.PAID);
        incomeDto.setNote("Hello");

        // When
        return incomeService.save(incomeDto);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testIncomeIsDeleted() {
        // Given
        IncomeDto incomeDto = persistIncome();

        // When
        incomeService.delete(incomeDto.getId(), incomeDto.getYardId());

        // Then
        incomeService.findOne(incomeDto.getId());
    }
}
