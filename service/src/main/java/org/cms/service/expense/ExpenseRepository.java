package org.cms.service.expense;

import org.cms.service.commons.PaymentState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("FROM Expense e WHERE yard.id=:yardId")
    Iterable<Expense> listByYard(@Param("yardId") long yardId);

    @Query("FROM Expense e WHERE id=:id AND yard.id=:yardId")
    Expense findByIdAndYardId(@Param("id") long id, @Param("yardId") long yardId);

    @Query("FROM Expense e WHERE e.status = :#{#status} ORDER BY YEAR(e.expiresAt)" +
            " DESC, MONTH(e.expiresAt) DESC, e.category")
    List<Expense> listByPaymentStateOrderedByYearAndMonthAndCategory(@Param("status") PaymentState status);

}