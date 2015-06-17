package org.cms.data.repository;

import org.cms.data.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

    <S extends User> S save(S user);

    void deleteAll();
}
