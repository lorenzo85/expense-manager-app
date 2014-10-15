package com.spring.cms.persistence.repository;

import com.spring.cms.persistence.domain.Expense;
import com.spring.cms.persistence.domain.PaymentState;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {

    @Query("FROM Expense e where e.status = :state ORDER BY YEAR(e.expiresAt) DESC, MONTH(e.expiresAt) DESC, e.category")
    Iterable<Expense> listExpensesOrdered(@Param("state") PaymentState state);

    @Query("FROM Expense e where yard.id=:yardId")
    Iterable<Expense> listByYard(@Param("yardId") long yardId);

    @Query("FROM Expense e where id=:id and yard.id=:yardId")
    Expense findByIdAndYardId(@Param("id") long id, @Param("yardId") long yardId);
}