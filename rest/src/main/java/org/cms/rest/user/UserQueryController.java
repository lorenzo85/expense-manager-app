package org.cms.rest.user;

import org.cms.service.user.UserAuthenticationDto;
import org.cms.service.user.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserQueryController {

    @RequestMapping("/auth/user")
    public UserDetails getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UserAuthenticationDto) {
            return ((UserAuthenticationDto) authentication).getDetails();
        }

        // Anonymous user support
        return new UserDto
                .Builder(authentication.getName())
                .build();
    }
}