package org.cms.service.income;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Query("FROM Income i where yard.id=:yardId")
    Iterable<Income> listByYard(@Param("yardId") long yardId);

    @Query("FROM Income i where id=:id and yard.id=:yardId")
    Income findByIdAndYardId(@Param("id") long id, @Param("yardId") long yardId);
}
