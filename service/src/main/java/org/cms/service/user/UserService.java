package org.cms.service.user;

import org.cms.service.commons.BaseService;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends BaseService<UserDto, Long>, UserDetailsService {

    void revokeRole(long userId, UserRole role);

    void grantRole(long userId, UserRole role);
}
