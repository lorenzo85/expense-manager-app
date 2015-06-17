package org.cms.data.service;

import org.cms.data.domain.User;
import org.cms.data.dto.UserDto;
import org.cms.data.repository.UserRepository;
import org.cms.data.utilities.IsValid;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
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
    public UserDto update(@IsValid UserDto dto) {
        findOneOrThrow(dto.getId());

        User user = mapper.map(dto, User.class);
        user = repo.save(user);

        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto save(@IsValid UserDto dto) {
        throwIfFound(dto.getId());

        User user = mapper.map(dto, User.class);
        user = repo.save(user);

        return mapper.map(user, UserDto.class);
    }

    @Override
    protected CrudRepository<User, Long> getRepository() {
        return repo;
    }
}
