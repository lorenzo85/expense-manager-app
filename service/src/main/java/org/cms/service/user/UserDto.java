package org.cms.service.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.cms.service.commons.Dto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

public class UserDto implements UserDetails, Dto<Long> {

    private long id;
    private String username;
    private String password;
    private boolean accountExpired;
    private boolean accountLocked;
    private boolean accountEnabled;
    private boolean credentialsExpired;
    private Collection<UserAuthorityDto> roles;

    public UserDto() {
    }

    public UserDto(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.accountLocked = builder.accountLocked;
        this.accountEnabled = builder.accountEnabled;
        this.accountExpired = builder.accountExpired;
        this.credentialsExpired = builder.credentialsExpired;
        this.roles = builder.authorityDtos;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
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
        return accountEnabled;
    }

    @Override
    @JsonIgnore
    public Long getIdentifier() {
        return id;
    }

    public long getId() {
        return id;
    }

    public Collection<UserAuthorityDto> getRoles() {
        return this.roles;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public boolean isAccountEnabled() {
        return accountEnabled;
    }

    public void setAccountEnabled(boolean accountEnabled) {
        this.accountEnabled = accountEnabled;
    }

    public boolean isCredentialsExpired() {
        return credentialsExpired;
    }

    public void setCredentialsExpired(boolean credentialsExpired) {
        this.credentialsExpired = credentialsExpired;
    }

    public void setRoles(Collection<UserAuthorityDto> roles) {
        this.roles = roles;
    }

    public static class Builder {

        private boolean accountExpired;
        private boolean accountLocked;
        private boolean accountEnabled;
        private boolean credentialsExpired;
        private String username;
        private String password;
        private Collection<UserAuthorityDto> authorityDtos = new HashSet<>();

        public Builder(String username) {
            this.username = username;
        }

        public Builder password(String password) {
            this.password = password;
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

        public Builder accountEnabled(boolean accountEnabled) {
            this.accountEnabled = accountEnabled;
            return this;
        }

        public Builder credentialsExpired(boolean credentialsExpired) {
            this.credentialsExpired = credentialsExpired;
            return this;
        }

        public Builder authority(Collection<UserAuthorityDto> authorityDtos) {
            this.authorityDtos.addAll(authorityDtos);
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }
    }
}
