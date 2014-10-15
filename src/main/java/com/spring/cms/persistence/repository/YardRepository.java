package com.spring.cms.persistence.repository;

import com.spring.cms.persistence.domain.Yard;
import org.springframework.data.repository.CrudRepository;

public interface YardRepository extends CrudRepository<Yard, Long> {
}
