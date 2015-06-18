package org.cms.service.expense;

import java.util.List;


public interface ExpenseRepositoryCustom {

    List<Expense> listUnpaidExpensesOrderedByYearAndMonthAndCategory();

}
