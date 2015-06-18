package org.cms.service.user;

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
    private UserService userService;
    @Autowired
    private UserTokenHandler tokenHandler;


    public void addAuthentication(HttpServletResponse response, UserAuthenticationDto userAuthenticationDto) throws UserTokenHandler.TokenProcessingException {
        final UserDetails user = userAuthenticationDto.getDetails();
        String token = tokenHandler.create(user);
        response.addHeader(tokenHeaderName, token);
    }

    public Authentication getAuthentication(HttpServletRequest request) throws UserTokenHandler.TokenProcessingException, UserTokenHandler.InvalidTokenException {
        Optional<String> token = ofNullable(request.getHeader(tokenHeaderName));
        if (token.isPresent()) {
            UserDto user = tokenHandler.parse(token.get(), UserDto.class);
            UserDto userDetails = (UserDto) userService.loadUserByUsername(user.getUsername());
            return new UserAuthenticationDto(userDetails);
        }
        return null;
    }
}