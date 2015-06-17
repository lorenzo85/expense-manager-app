package org.cms.data.app.rest;

import org.cms.data.dto.UserAuthenticationDto;
import org.cms.data.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserQueryController {

    @RequestMapping("/auth/user")
    public UserDto getUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UserAuthenticationDto) {
            return ((UserAuthenticationDto) authentication).getDetails();
        }

        // Anonymous user support
        return new UserDto(authentication.getName());
    }
}
