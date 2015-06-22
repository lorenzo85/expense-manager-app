package org.cms.core.expense;

import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import static java.util.Collections.singletonList;
import static org.cms.core.expense.ExpenseCategory.CHECKS;
import static org.joda.money.CurrencyUnit.CAD;
import static org.joda.money.Money.of;
import static org.junit.Assert.assertTrue;

public class ExpenseDeadlineConverterTest {

    DozerBeanMapper mapper;

    @Before
    public void setUp() {
        mapper = new DozerBeanMapper();
        mapper.setMappingFiles(singletonList("mapping.xml"));
    }

    @Test
    public void shouldConvertToDto() {
        // Given
        Expense expense = new Expense();
        expense.amount = of(CAD, 234.43);
        expense.category = CHECKS;
        expense.id = 20;
        ExpensesGroupByYearAndMonth expenseGroup = ExpensesGroupByYearAndMonth.builder(CAD)
                .month("Jan")
                .year("2004")
                .expenses(singletonList(expense))
                .build();

        // When
        DeadlinesDto dto = mapper.map(expenseGroup, DeadlinesDto.class);

        // Then
        assertTrue(dto.getExpenses().size() == 1);
        assertTrue(dto.getExpensesSumsForCategory().size() == 1);
        assertTrue(dto.getExpensesSumsForCategory().containsKey(CHECKS));
    }
}
