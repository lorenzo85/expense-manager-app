package org.cms.rest.user;

import org.cms.rest.config.security.CurrentUserDetails;
import org.cms.rest.config.security.UserAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserQueryController {

    @RequestMapping("/auth/user")
    public LoggedInUser getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UserAuthentication) {
            CurrentUserDetails userDetails = ((UserAuthentication) authentication).getDetails();
            return new LoggedInUser(userDetails);
        }

        // Anonymous user support
        return new LoggedInUser(authentication.getName());
    }
}
