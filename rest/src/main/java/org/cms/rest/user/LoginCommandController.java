package org.cms.rest.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginCommandController {

    @RequestMapping("/auth/login")
    public void login() {
        throw new UnsupportedOperationException("This method is here only for documentation purposes. " +
                "The Login process is handled by the LoginFilter configured in SecurityConfig");
    }
}
