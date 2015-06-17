package org.cms.data.dto;

import org.springframework.security.core.GrantedAuthority;

public class UserAuthorityDto implements GrantedAuthority {

    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}
