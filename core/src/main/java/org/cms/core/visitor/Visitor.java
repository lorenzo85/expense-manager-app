package org.cms.core.visitor;


import org.cms.core.expense.Expense;
import org.cms.core.income.Income;

public interface Visitor {

    void visit(Expense expense);

    void visit(Income income);

}
