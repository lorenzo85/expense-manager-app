package org.cms.rest.config.security;

import org.cms.core.user.UserDto;
import org.cms.core.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public CurrentUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(format("Username %s was not found!", username));
        }

        return new CurrentUserDetails.Builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .enabled(user.isAccountEnabled())
                .accountLocked(user.isAccountLocked())
                .accountExpired(user.isAccountExpired())
                .credentialsExpired(user.isCredentialsExpired())
                .authorities(user.getRoles())
                .build();
    }

}
