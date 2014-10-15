package com.spring.cms.persistence.repository;

import com.spring.cms.persistence.domain.Income;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IncomeRepository extends CrudRepository<Income, Long> {

    @Query("FROM Income i where yard.id=:yardId")
    Iterable<Income> listByYard(@Param("yardId") long yardId);

    @Query("FROM Income i where id=:id and yard.id=:yardId")
    Income findByIdAndYardId(@Param("id") long id, @Param("yardId") long yardId);
}
