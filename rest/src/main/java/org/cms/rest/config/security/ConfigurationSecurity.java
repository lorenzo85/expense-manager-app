package org.cms.rest.config.security;

import org.cms.service.user.UserAuthenticationService;
import org.cms.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.Filter;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpMethod.*;

@EnableWebSecurity
@Configuration
@Order(HIGHEST_PRECEDENCE)
public class ConfigurationSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;
    @Autowired
    private UserAuthenticationService userAuthenticationService;

    public ConfigurationSecurity() {
        super(true);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling().and()
                .anonymous().and()
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers("/favicon.ico").permitAll()
                    .antMatchers(POST, "/auth/login").permitAll()
                    .antMatchers(GET, "/auth/user").permitAll()
                    .antMatchers(OPTIONS, "/**").permitAll()
                    .anyRequest().fullyAuthenticated()
                .and()
                .addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    private Filter loginFilter() throws Exception {
        AntPathRequestMatcher pathRequestMatcher = new AntPathRequestMatcher("/auth/login", POST.name());
        return new LoginFilter(pathRequestMatcher, userService, authenticationManager(), userAuthenticationService);
    }

    private Filter authFilter() {
        return new AuthFilter(userAuthenticationService);
    }
}