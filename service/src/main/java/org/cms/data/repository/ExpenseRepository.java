package org.cms.data.repository;

import org.cms.data.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends ExpenseRepositoryCustom, JpaRepository<Expense, Long> {

    @Query("FROM Expense e WHERE yard.id=:yardId")
    Iterable<Expense> listByYard(@Param("yardId") long yardId);

    @Query("FROM Expense e WHERE id=:id AND yard.id=:yardId")
    Expense findByIdAndYardId(@Param("id") long id, @Param("yardId") long yardId);
}