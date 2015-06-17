package org.cms.data.repository;


import org.cms.data.domain.Income;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepository extends CrudRepository<Income, Long> {

    @Query("FROM Income i where yard.id=:yardId")
    Iterable<Income> listByYard(@Param("yardId") long yardId);

    @Query("FROM Income i where id=:id and yard.id=:yardId")
    Income findByIdAndYardId(@Param("id") long id, @Param("yardId") long yardId);
}
