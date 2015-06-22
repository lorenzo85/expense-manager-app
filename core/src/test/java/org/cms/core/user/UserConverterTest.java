package org.cms.core.user;

import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Random;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.cms.core.ConfigurationService.DOZER_MAPPER_SPEC;
import static org.cms.core.user.Role.ROLE_ADMIN;
import static org.cms.core.user.Role.ROLE_USER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class UserConverterTest {

    DozerBeanMapper mapper;
    Random random;

    @Before
    public void setUp() {
        random = new Random();
        mapper = new DozerBeanMapper();
        mapper.setMappingFiles(singletonList(DOZER_MAPPER_SPEC));
    }

    @Test
    public void shouldMapCorrectlyToUserDto() {
        // Given
        User user = new User();
        user.id = random.nextLong();
        user.username = "aUsername";
        user.password = "aPassword";
        user.accountLocked = true;
        user.accountEnabled = true;
        user.accountExpired = true;
        user.credentialsExpired = true;

        UserRole adminRole = createUserRole(ROLE_ADMIN, user);
        UserRole userRole = createUserRole(ROLE_USER, user);
        user.roles = newHashSet(adminRole, userRole);

        // When
        UserDto dto = mapper.map(user, UserDto.class);

        // Then
        Collection<Role> dtoRoles = dto.getRoles();
        dtoRoles.contains(adminRole.role);
        dtoRoles.contains(userRole.role);
        assertEquals(dto.id, user.id);
        assertEquals(dto.username, user.username);
        assertEquals(dto.password, user.password);
        assertEquals(dto.accountLocked, user.accountLocked);
        assertEquals(dto.accountEnabled, user.accountEnabled);
        assertEquals(dto.accountExpired, user.accountExpired);
        assertEquals(dto.credentialsExpired, user.credentialsExpired);
    }

    @Test
    public void shouldMapCorrectlyToUserExcludingRoles() {
        // Given
        UserDto dto = UserDto.builder()
                .username("username")
                .password("aPassword")
                .accountLocked(true)
                .accountEnabled(true)
                .accountExpired(true)
                .credentialsExpired(true)
                .build();
        dto.id = random.nextLong();
        dto.roles = asList(ROLE_ADMIN, ROLE_USER);

        // When
        User user = mapper.map(dto, User.class);

        // Then
        assertEquals(user.id, dto.id);
        assertEquals(user.username, dto.username);
        assertEquals(user.password, dto.password);
        assertEquals(user.accountLocked, dto.accountLocked);
        assertEquals(user.accountEnabled, dto.accountEnabled);
        assertEquals(user.accountExpired, dto.accountExpired);
        assertEquals(user.credentialsExpired, dto.credentialsExpired);
        assertTrue(user.roles.isEmpty());
    }

    private UserRole createUserRole(Role role, User user) {
        UserRole userRole = new UserRole();
        userRole.id = random.nextLong();
        userRole.role = role;
        userRole.user = user;
        return userRole;
    }
}
