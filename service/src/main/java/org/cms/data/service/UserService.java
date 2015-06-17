package org.cms.data.service;

import org.cms.data.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends BaseService<UserDto, Long>, UserDetailsService {
}
