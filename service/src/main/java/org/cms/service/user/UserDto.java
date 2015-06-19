package org.cms.service.user;

import org.cms.service.commons.Dto;

import java.util.Collection;
import java.util.HashSet;

public class UserDto implements Dto<Long> {

    long id;
    boolean accountLocked;
    boolean accountExpired;
    boolean accountEnabled;
    boolean credentialsExpired;

    String username;
    String password;
    Collection<Role> roles;

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
    public Long getId() {
        return id;
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public static class Builder {

        private boolean accountExpired;
        private boolean accountLocked;
        private boolean accountEnabled;
        private boolean credentialsExpired;
        private String username;
        private String password;
        private Collection<Role> authorityDtos = new HashSet<>();

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

        public UserDto build() {
            return new UserDto(this);
        }
    }
}
