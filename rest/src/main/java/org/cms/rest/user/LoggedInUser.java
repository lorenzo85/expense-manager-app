package org.cms.rest.user;

import org.cms.rest.config.security.CurrentUserDetails;
import org.cms.core.user.Role;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class LoggedInUser {

    String username;
    Collection<Role> roles = new HashSet<>();

    public LoggedInUser() {
    }

    public LoggedInUser(String username) {
        this.username = username;
    }

    public LoggedInUser(CurrentUserDetails currentUserDetails) {
        this.username = currentUserDetails.getUsername();
        this.roles.addAll(currentUserDetails.getAuthorities().stream()
                .map(authority -> Role.valueOf(authority.getAuthority()))
                .collect(Collectors.toList()));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
