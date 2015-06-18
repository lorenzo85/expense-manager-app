package org.cms.service.user;

import org.springframework.security.core.GrantedAuthority;

public class UserAuthorityDto implements GrantedAuthority {

    private long id;
    private UserDto userDto;
    private String authority;

    public UserAuthorityDto() {
    }

    public UserAuthorityDto(UserDto userDto, String authority) {
        this.authority = authority;
        this.userDto = userDto;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
