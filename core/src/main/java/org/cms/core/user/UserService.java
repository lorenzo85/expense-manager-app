package org.cms.core.user;

import org.cms.core.commons.BaseService;

public interface UserService extends BaseService<UserDto, Long> {

    UserDto findByUsername(String username);

    void revokeRole(long userId, Role role);

    void grantRole(long userId, Role role);
}
