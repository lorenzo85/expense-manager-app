package org.cms.service.user;

import org.cms.service.AbstractBaseServiceTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.cms.service.user.Role.ROLE_ADMIN;
import static org.cms.service.user.Role.ROLE_USER;
import static org.junit.Assert.*;

public class UserServiceTest extends AbstractBaseServiceTest {

    private static final String USERNAME = "aTestUser1";
    private static final String PASSWORD = "aPassword";

    @Autowired
    UserService userService;

    UserDto testUser;

    @Before
    public void setUp() {
        testUser = UserDto.builder()
                .username(USERNAME)
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
        assertNotEquals(0, testUser.getId().longValue());
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
        UserDto user = UserDto.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .accountEnabled(true)
                .accountLocked(true)
                .accountExpired(true)
                .credentialsExpired(true)
                .build();

        // When
        UserDto persisted = userService.save(user);

        // Then
        UserDto foundUser = userService.findOne(persisted.getId());
        assertTrue(reflectionEquals(user, foundUser, "id", "roles"));
        assertTrue(foundUser.getRoles().isEmpty());
    }

    @Test
    public void shouldCorrectlyPersistAuthority() {
        // Given
        Role role = ROLE_USER;
        UserDto user = UserDto.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .accountEnabled(true)
                .accountLocked(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .build();
        user.roles = singletonList(role);

        // When
        UserDto persistedUser = userService.save(user);

        // Then
        UserDto foundUser = userService.findOne(persistedUser.getId());
        foundUser.roles.contains(role);
    }

    @Test
    public void shouldCorrectlyUpdateByAddingRole() {
        // Given
        Role adminRole = ROLE_ADMIN;
        Role userRole = ROLE_USER;
        UserDto user = UserDto.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();
        user.roles = singletonList(adminRole);

        UserDto persisted = userService.save(user);
        persisted.roles.add(userRole);

        // When
        persisted = userService.update(persisted);

        // Then
        UserDto foundUser = userService.findOne(persisted.getId());
        assertTrue(reflectionEquals(persisted, foundUser, "roles"));
        assertContains(foundUser.getRoles(), adminRole);
        assertContains(persisted.getRoles(), userRole);
    }

    @Test
    public void shouldCorrectlyUpdateByRemovingRole() {
        // Given
        Role adminRole = ROLE_ADMIN;
        Role userRole = ROLE_USER;
        UserDto user = UserDto.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();
        user.roles = asList(adminRole, userRole);

        UserDto persisted = userService.save(user);
        persisted.roles.remove(adminRole);

        // When
        persisted = userService.update(persisted);

        // Then
        UserDto foundUser = userService.findOne(persisted.getId());
        assertTrue(foundUser.getRoles().size() == 1);
        assertFalse(foundUser.getRoles().contains(adminRole));
    }

    @Test
    public void shouldCorrectlyRevokeRole() {
        // Given
        Role adminRole = ROLE_ADMIN;
        Role userRole = ROLE_USER;
        UserDto user = UserDto.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();
        user.roles = asList(adminRole, userRole);

        UserDto persisted = userService.save(user);

        // When
        userService.revokeRole(persisted.getId(), adminRole);

        // Then
        UserDto found = userService.findOne(persisted.getId());
        assertTrue(found.getRoles().size() == 1);
        assertTrue(found.getRoles().contains(userRole));
    }

    @Test
    public void shouldCorrectlyGrantRole() {
        // Given
        Role adminRole = ROLE_ADMIN;
        Role userRole = ROLE_USER;
        UserDto user = UserDto.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();
        user.roles = singletonList(adminRole);

        UserDto persisted = userService.save(user);

        // When
        userService.grantRole(persisted.getId(), userRole);

        // Then
        UserDto found = userService.findOne(persisted.getId());
        assertTrue(found.getRoles().size() == 2);
        assertTrue(found.getRoles().contains(adminRole));
        assertTrue(found.getRoles().contains(userRole));

    }

    @Test
    public void shouldNotAddTwiceSameRole() {
        // Given
        Role adminRole = ROLE_ADMIN;
        UserDto user = UserDto.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();
        user.roles = singletonList(adminRole);

        UserDto persisted = userService.save(user);

        // When
        userService.grantRole(persisted.getId(), adminRole);

        // Then
        UserDto found = userService.findOne(persisted.getId());
        assertTrue(found.getRoles().size() == 1);
        assertTrue(found.getRoles().contains(adminRole));
    }
}
