package org.cms.data.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cms.data.dto.UserAuthenticationDto;
import org.cms.data.dto.UserDto;
import org.cms.data.service.AuthenticationService;
import org.cms.data.utilities.JsonTokenHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Scanner;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    private final UserDetailsService userService;
    private final AuthenticationService authenticationService;

    protected LoginFilter(AntPathRequestMatcher requestMatcher,
                          UserDetailsService userService,
                          AuthenticationManager authenticationManager,
                          AuthenticationService authenticationService) {
        super(requestMatcher);
        this.userService = userService;
        this.authenticationService = authenticationService;
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
        final UserDetails authenticatedUser = userService.loadUserByUsername(authResult.getName());
        final UserAuthenticationDto userAuthenticationDto = new UserAuthenticationDto(authenticatedUser);

        try {
            authenticationService.addAuthentication(response, userAuthenticationDto);
            SecurityContextHolder.getContext().setAuthentication(userAuthenticationDto);
        } catch (JsonTokenHandler.TokenProcessingException e) {
            throw new ServletException(e);
        }
    }

    private String extractBody(HttpServletRequest request) throws IOException {
        Scanner scanner = new Scanner(request.getInputStream(), "UTF-8").useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }
}
