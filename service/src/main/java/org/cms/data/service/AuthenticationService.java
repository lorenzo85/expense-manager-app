package org.cms.data.service;

import org.cms.data.dto.UserAuthenticationDto;
import org.cms.data.dto.UserDto;
import org.cms.data.utilities.JsonTokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;
    @Autowired
    private Environment environment;
    @Autowired
    private JsonTokenHandler tokenHandler;

    public void addAuthentication(HttpServletResponse response, UserAuthenticationDto userAuthenticationDto) throws JsonTokenHandler.TokenProcessingException {
        final UserDetails user = userAuthenticationDto.getDetails();
        String token = tokenHandler.create(user);
        response.addHeader(getTokenHeader(), token);
    }

    public Authentication getAuthentication(HttpServletRequest request) throws JsonTokenHandler.TokenProcessingException, JsonTokenHandler.InvalidTokenException {
        Optional<String> token = ofNullable(request.getHeader(getTokenHeader()));
        if (token.isPresent()) {
            UserDto user = tokenHandler.parse(token.get(), UserDto.class);
            UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
            return new UserAuthenticationDto(userDetails);
        }
        return null;
    }

    private String getTokenHeader() {
        return environment.getProperty("token.header.name");
    }
}