package org.cms.data.repository;


import org.cms.data.domain.Expense;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

import static org.cms.data.domain.PaymentState.UNPAID;

@Repository
public class ExpenseRepositoryImpl implements ExpenseRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Expense> listUnpaidExpensesOrderedByYearAndMonthAndCategory() {
        TypedQuery<Expense> query = em.createQuery("FROM Expense e WHERE e.status=" + UNPAID.getValue() +
                " ORDER BY YEAR(e.expiresAt) DESC, MONTH(e.expiresAt) DESC, e.category", Expense.class);
        return query.getResultList();
    }
}
