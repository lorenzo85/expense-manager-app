package org.cms.rest.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class UserAuthenticationService {

    @Value("${token.header.name}")
    private String tokenHeaderName;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserTokenHandler tokenHandler;


    public void addAuthentication(HttpServletResponse response, UserAuthentication userAuthentication) throws UserTokenHandler.TokenProcessingException {
        final CurrentUserDetails user = userAuthentication.getDetails();
        String token = tokenHandler.create(user);
        response.addHeader(tokenHeaderName, token);
    }

    public Authentication getAuthentication(HttpServletRequest request) throws UserTokenHandler.TokenProcessingException, UserTokenHandler.InvalidTokenException {
        Optional<String> token = ofNullable(request.getHeader(tokenHeaderName));
        if (token.isPresent()) {
            UserDetails parsedUser = tokenHandler.parse(token.get(), CurrentUserDetails.class);
            CurrentUserDetails userDetails = userDetailsService.loadUserByUsername(parsedUser.getUsername());
            return new UserAuthentication(userDetails);
        }
        return null;
    }
}