package org.cms.rest.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cms.core.user.UserDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    private final UserDetailsService userService;
    private final UserAuthenticationService userAuthenticationService;

    protected LoginFilter(AntPathRequestMatcher requestMatcher,
                          UserDetailsService userService,
                          AuthenticationManager authenticationManager,
                          UserAuthenticationService userAuthenticationService) {
        super(requestMatcher);
        this.userService = userService;
        this.userAuthenticationService = userAuthenticationService;
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
            final UserDto user = new ObjectMapper().readValue(request.getInputStream(), UserDto.class);
            final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            return getAuthenticationManager().authenticate(loginToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        final CurrentUserDetails userDetails = userService.loadUserByUsername(authResult.getName());
        final UserAuthentication userAuthentication = new UserAuthentication(userDetails);

        try {
            userAuthenticationService.addAuthentication(response, userAuthentication);
            SecurityContextHolder.getContext().setAuthentication(userAuthentication);
        } catch (UserTokenHandler.TokenProcessingException e) {
            throw new ServletException(e);
        }
    }
}
