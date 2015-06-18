package org.cms.service.user;

import org.cms.service.AbstractBaseServiceTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.cms.service.user.UserRole.ROLE_ADMIN;
import static org.cms.service.user.UserRole.ROLE_USER;
import static org.junit.Assert.*;

public class UserServiceTest extends AbstractBaseServiceTest {

    private static final String USERNAME = "aTestUser1";
    private static final String PASSWORD = "aPassword";

    @Autowired
    UserService userService;

    UserDto testUser;

    @Before
    public void setUp() {
        testUser = new UserDto.Builder(USERNAME)
                .password(PASSWORD)
                .accountEnabled(true)
                .accountLocked(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .build();
    }

    @Test
    public void shouldAssignIdToPersistedUser() {
        // When
        testUser = userService.save(testUser);

        // Then
        assertNotEquals(0, testUser.getId());
    }

    @Test
    public void shouldFindPersistedUserById() {
        // Given
        testUser = userService.save(testUser);

        // When
        UserDto foundUser = userService.findOne(testUser.getId());

        // Then
        assertNotNull(foundUser);
    }

    @Test
    public void shouldCorrectlyPersistUserData() {
        // Given
        UserDto user = new UserDto.Builder(USERNAME)
                .password(PASSWORD)
                .accountEnabled(true)
                .accountLocked(true)
                .accountExpired(true)
                .credentialsExpired(true)
                .build();

        // When
        user = userService.save(user);

        // Then
        UserDto foundUser = userService.findOne(user.getId());
        reflectionEquals(user, foundUser);
    }

    @Test
    public void shouldCorrectlyPersistAuthority() {
        // Given
        UserRole role = ROLE_USER;
        UserDto user = new UserDto.Builder(USERNAME)
                .password(PASSWORD)
                .accountEnabled(true)
                .accountLocked(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .build();

        user.getRoles().add(new UserAuthorityDto(user, role.toString()));

        // When
        user = userService.save(user);

        // Then
        UserDto persistedUser = userService.findOne(user.getId());
        Collection<? extends GrantedAuthority> persistedUserAuthorities = persistedUser.getAuthorities();
        assertRoleExistsInAuthorities(persistedUserAuthorities, role);
    }

    @Test
    public void shouldCorrectlyUpdateAuthority() {
        // Given
        UserRole admin = ROLE_ADMIN;
        UserRole user = ROLE_USER;

        testUser.getRoles().add(new UserAuthorityDto(testUser, admin.toString()));
        testUser = userService.save(testUser);

        testUser.getRoles().add(new UserAuthorityDto(testUser, user.toString()));
        userService.update(testUser);

        UserDto persistedUser = userService.findOne(testUser.getId());
        assertRoleExistsInAuthorities(persistedUser.getAuthorities(), admin, user);
    }

    @Test
    public void shouldCorrectlyRevokeRole() {
        // Given
        UserRole admin = ROLE_ADMIN;
        UserRole user = ROLE_USER;
        testUser.getRoles().addAll(asList(
                new UserAuthorityDto(testUser, admin.toString()),
                new UserAuthorityDto(testUser, user.toString())));
        testUser = userService.save(testUser);

        // When
        userService.revokeRole(testUser.getId(), admin);

        // Then
        testUser = userService.findOne(testUser.getId());
        assertRoleDoesNotExistsInAuthorities(testUser.getAuthorities(), admin);
    }

    @Test
    public void shouldCorrectlyGrantRole() {
        // Given
        UserRole user = ROLE_USER;
        testUser = userService.save(testUser);

        // When
        userService.grantRole(testUser.getId(), user);

        // Then
        testUser = userService.findOne(testUser.getId());
        assertRoleExistsInAuthorities(testUser.getAuthorities(), user);
    }

    @Test
    public void shouldNotAddTwiceSameRole() {
        // Given
        UserRole user = ROLE_ADMIN;
        testUser.getRoles().add(new UserAuthorityDto(testUser, user.toString()));
        testUser = userService.save(testUser);

        // When
        userService.grantRole(testUser.getId(), user);

        // Then
        testUser = userService.findOne(testUser.getId());
        assertEquals(1, testUser.getRoles().size());
    }

    private void assertRoleDoesNotExistsInAuthorities(Collection<? extends GrantedAuthority> persistedUserAuthorities, UserRole role) {
        boolean found = false;
        for (GrantedAuthority authority : persistedUserAuthorities) {
            if (authority.getAuthority().equals(role.toString())) {
                found = true;
            }
        }
        assertFalse(found);
    }

    private void assertRoleExistsInAuthorities(Collection<? extends GrantedAuthority> persistedUserAuthorities, UserRole... roles) {
        List<UserRole> founds = new ArrayList<>();
        for (GrantedAuthority authority : persistedUserAuthorities) {
            UserRole userRole = UserRole.valueOf(authority.getAuthority());
            for (UserRole role : roles) {
                if (role.equals(userRole)) {
                    founds.add(role);
                }
            }
        }
        assertEquals(founds.size(), roles.length);
    }
}
