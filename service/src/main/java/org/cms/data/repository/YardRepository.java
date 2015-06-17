package org.cms.data.repository;


import org.cms.data.domain.Yard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YardRepository extends CrudRepository<Yard, Long> {
}
