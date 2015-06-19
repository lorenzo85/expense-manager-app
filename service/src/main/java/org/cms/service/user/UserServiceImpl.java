package org.cms.service.user;

import org.cms.service.commons.BaseAbstractService;
import org.cms.service.commons.IsValid;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl extends BaseAbstractService<UserDto, User, Long> implements UserService {

    @Autowired
    protected Mapper mapper;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto save(@IsValid UserDto dto) {
        throwIfFound(dto.getId());
        return saveOrUpdate(dto);
    }

    @Override
    public UserDto update(@IsValid UserDto dto) {
        findOneOrThrow(dto.getId());
        return saveOrUpdate(dto);
    }

    @Override
    public UserDto findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent() ? mapper.map(user.get(), UserDto.class) : null;
    }

    @Override
    public void revokeRole(long userId, Role role) {
        User user = userRepository.findOne(userId);
        user.revokeRole(role);
        userRepository.save(user);
    }

    @Override
    public void grantRole(long userId, Role role) {
        User user = userRepository.findOne(userId);
        user.grantRole(role);
        userRepository.save(user);
    }

    @Override
    protected JpaRepository<User, Long> getRepository() {
        return userRepository;
    }

    // Note that the mapper DOES NOT map from UserDto to User object the role!
    // When we save the first time the user Object the roles are all removed.
    // With the grantRole(role) call we then update the roles for the user and save again
    // with the updated roles.
    private UserDto saveOrUpdate(@IsValid UserDto dto) {
        User user = mapper.map(dto, User.class);
        user = userRepository.save(user);

        for (Role role : dto.getRoles()) {
            user.grantRole(role);
        }

        user = userRepository.save(user);
        return mapper.map(user, UserDto.class);
    }
}
