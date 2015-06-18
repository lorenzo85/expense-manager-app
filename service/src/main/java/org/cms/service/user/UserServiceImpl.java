package org.cms.service.user;

import org.cms.service.commons.BaseAbstractService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@Service
public class UserServiceImpl extends BaseAbstractService<UserDto, User, Long> implements UserService {

    @Autowired
    protected Mapper mapper;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException(format("Username %s was not found!", username));
        }
        return mapper.map(user.get(), UserDto.class);
    }

    @Override
    public void revokeRole(long userId, UserRole role) {
        User user = userRepository.findOne(userId);
        user.revokeRole(role);
        userRepository.save(user);
    }

    @Override
    public void grantRole(long userId, UserRole role) {
        User user = userRepository.findOne(userId);
        user.grantRole(role);
        userRepository.save(user);
    }

    @Override
    protected JpaRepository<User, Long> getRepository() {
        return userRepository;
    }
}
