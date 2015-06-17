package org.cms.data.service;

import org.cms.data.domain.User;
import org.cms.data.dto.UserDto;
import org.cms.data.repository.UserRepository;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@Service
public class UserServiceImpl extends AbstractService<UserDto, User, Long> implements UserService {

    @Autowired
    protected Mapper mapper;
    @Autowired
    private UserRepository repo;

    @Override
    public UserDto loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repo.findByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException(format("Username %s was not found!", username));
        }
        return mapper.map(user.get(), UserDto.class);
    }

    @Override
    protected JpaRepository<User, Long> getRepository() {
        return repo;
    }
}
