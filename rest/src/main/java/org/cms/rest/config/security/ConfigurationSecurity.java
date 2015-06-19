package org.cms.rest.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.Filter;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpMethod.*;

@EnableWebSecurity
@Configuration
@Order(HIGHEST_PRECEDENCE)
public class ConfigurationSecurity extends WebSecurityConfigurerAdapter {

    @Value("${token.secret}")
    private String tokenSecret;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserAuthenticationService userAuthenticationService;

    public ConfigurationSecurity() {
        super(true);
    }

    @SuppressWarnings("unchecked")
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
                .addFilterBefore(loginFilter(), (Class<? extends Filter>) UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(authFilter(), (Class<? extends Filter>) UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    @Scope("singleton")
    public UserTokenHandler getTokenHandler() throws InvalidKeyException, NoSuchAlgorithmException {
        return new UserTokenHandler(tokenSecret);
    }

    private Filter loginFilter() throws Exception {
        AntPathRequestMatcher pathRequestMatcher = new AntPathRequestMatcher("/auth/login", POST.name());
        return (Filter) new LoginFilter(pathRequestMatcher, userDetailsService, authenticationManager(), userAuthenticationService);
    }

    private Filter authFilter() {
        return new AuthFilter(userAuthenticationService);
    }
}
