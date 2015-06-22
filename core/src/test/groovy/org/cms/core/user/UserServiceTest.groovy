package org.cms.core.user

import org.cms.core.BaseSpecification

import static java.util.Arrays.*
import static java.util.Collections.*
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals
import static org.cms.core.user.Role.*

class UserServiceTest extends BaseSpecification {

    def username = "aTestUser1";
    def password = "aPassword";
    def testUser;

    void setup() {
        testUser = UserDto.builder()
                .username(username)
                .password(password)
                .accountEnabled(true)
                .accountLocked(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .build();
    }

    def "Should assign id to persisted user"() {
        when:
        testUser = userService.save(testUser)

        then:
        testUser.id != 0
    }

    def "Should find persisted user by id"() {
        given:
        testUser = userService.save(testUser)

        when:
        def persisted = userService.findOne(testUser.getId())

        then:
        persisted != null
    }

    def "Should correctly persist user date"() {
        given:
        testUser = userService.save(testUser)

        when:
        def persisted = userService.findOne(testUser.getId())

        then:
        reflectionEquals(testUser, persisted, "id", "roles")
        persisted.roles.isEmpty()
    }

    def "Should correctly persist role"() {
        given:
        Role role = ROLE_USER
        testUser.roles = singletonList(role)

        when:
        testUser = userService.save(testUser)
        def foundUser = userService.findOne(testUser.id)

        then:
        foundUser.roles.contains(role)
    }

    def "Should correctly update by adding new role"() {
        given:
        Role adminRole = ROLE_ADMIN
        Role userRole = ROLE_USER
        testUser.roles = singletonList(adminRole)
        testUser = userService.save(testUser)

        when:
        testUser.roles.add(userRole)
        userService.update(testUser)
        def foundUser = userService.findOne(testUser.getId())

        then:
        reflectionEquals(testUser, foundUser, "roles")
        foundUser.roles.contains(adminRole)
        foundUser.roles.contains(userRole)
    }

    def "Should correctly update by removing role"() {
        given:
        def adminRole = ROLE_ADMIN
        def userRole = ROLE_USER
        testUser.roles = asList(adminRole, userRole)
        testUser = userService.save(testUser)
        testUser.roles.remove(userRole)

        when:
        userService.update(testUser)
        def foundUser = userService.findOne(testUser.getId())

        then:
        foundUser.roles.size() == 1
        foundUser.roles.contains(adminRole)
    }

    def "Should correctly revoke role"() {
        given:
        def adminRole = ROLE_ADMIN;
        def userRole = ROLE_USER;
        testUser.roles = asList(adminRole, userRole)
        testUser = userService.save(testUser)

        when:
        userService.revokeRole(testUser.getId(), adminRole)
        def foundUser = userService.findOne(testUser.getId())

        then:
        foundUser.roles.size() == 1
        foundUser.roles.contains(userRole)
    }

    def "Should correctly grant role"() {
        given:
        def adminRole = ROLE_ADMIN;
        def userRole = ROLE_USER;
        testUser.roles = singletonList(adminRole)
        testUser = userService.save(testUser)

        when:
        userService.grantRole(testUser.getId(), userRole)
        def foundUser = userService.findOne(testUser.getId())

        then:
        foundUser.roles.size() == 2
        foundUser.roles.contains(adminRole)
        foundUser.roles.contains(userRole)
    }

    def "Should not add twice same role"() {
        given:
        Role adminRole = ROLE_ADMIN
        testUser.roles = singletonList(adminRole)
        testUser = userService.save(testUser)

        when:
        userService.grantRole(testUser.id, adminRole)
        def foundUser = userService.findOne(testUser.id)

        then:
        foundUser.roles.size() == 1
        foundUser.roles.contains(adminRole)
    }
}
