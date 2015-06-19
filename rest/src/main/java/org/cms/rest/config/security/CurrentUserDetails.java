package org.cms.rest.config.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.cms.service.user.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class CurrentUserDetails implements UserDetails {

    private String username;
    private String password;
    private boolean enabled;
    private boolean accountExpired;
    private boolean accountLocked;
    private boolean credentialsExpired;
    private Collection<GrantedAuthority> authorities;

    public CurrentUserDetails() {
    }

    private CurrentUserDetails(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.enabled = builder.enabled;
        this.authorities = builder.authorities;
        this.accountLocked = builder.accountLocked;
        this.accountExpired = builder.accountExpired;
        this.credentialsExpired = builder.credentialsExpired;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return !accountExpired;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return enabled;
    }

    public static class Builder {
        private String username;
        private String password;
        private boolean enabled;
        private boolean accountExpired;
        private boolean accountLocked;
        private boolean credentialsExpired;
        private Collection<GrantedAuthority> authorities = new HashSet<>();

        public Builder() {
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public Builder accountExpired(boolean accountExpired) {
            this.accountExpired = accountExpired;
            return this;
        }

        public Builder accountLocked(boolean accountLocked) {
            this.accountLocked = accountLocked;
            return this;
        }

        public Builder credentialsExpired(boolean credentialsExpired) {
            this.credentialsExpired = credentialsExpired;
            return this;
        }

        public Builder authorities(Collection<Role> roles) {
            this.authorities.addAll(roles.stream()
                    .map(role -> new CurrentUserAuthority(role.toString()))
                    .collect(Collectors.toList()));
            return this;
        }

        public CurrentUserDetails build() {
            return new CurrentUserDetails(this);
        }
    }

}
