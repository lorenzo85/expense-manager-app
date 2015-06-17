package org.cms.data.repository;


import org.cms.data.domain.Yard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YardRepository extends JpaRepository<Yard, Long> {
}
