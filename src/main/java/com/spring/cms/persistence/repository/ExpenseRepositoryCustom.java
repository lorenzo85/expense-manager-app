package com.spring.cms.persistence.repository;

import com.spring.cms.persistence.domain.Expense;

import java.util.List;

public interface ExpenseRepositoryCustom {

    List<Expense> listUnpaidExpensesOrderedByYearAndMonthAndCategory();

}
