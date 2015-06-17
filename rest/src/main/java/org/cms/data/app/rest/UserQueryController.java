package org.cms.data.app.rest;

import org.cms.data.dto.UserAuthenticationDto;
import org.cms.data.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserQueryController {

    @RequestMapping("/user")
    public UserDetails getUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UserAuthenticationDto) {
            return ((UserAuthenticationDto) authentication).getDetails();
        }

        // Anonymous user support
        return new UserDto(authentication.getName());
    }
}
