package org.cms.service.user;

import lombok.*;
import org.cms.service.commons.Dto;

import java.util.Collection;

import static java.util.Collections.emptyList;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Dto<Long> {

    long id;
    boolean accountLocked;
    boolean accountExpired;
    boolean accountEnabled;
    boolean credentialsExpired;

    String username;
    String password;
    Collection<Role> roles = emptyList();

    @Override
    public Long getId() {
        return id;
    }


    public Collection<Role> getRoles() {
        if (roles == null) {
            this.roles = emptyList();
        }
        return this.roles;
    }
}
