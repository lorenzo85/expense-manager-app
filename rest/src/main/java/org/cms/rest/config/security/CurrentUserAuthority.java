package org.cms.rest.config.security;

import org.springframework.security.core.GrantedAuthority;

class CurrentUserAuthority implements GrantedAuthority {

    private String authority;

    public CurrentUserAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
