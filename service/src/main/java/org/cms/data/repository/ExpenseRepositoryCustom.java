package org.cms.data.repository;

import org.cms.data.domain.Expense;

import java.util.List;


public interface ExpenseRepositoryCustom {

    List<Expense> listUnpaidExpensesOrderedByYearAndMonthAndCategory();

}
