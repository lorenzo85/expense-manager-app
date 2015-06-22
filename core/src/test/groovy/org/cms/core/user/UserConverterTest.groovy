package org.cms.core.user
import org.cms.core.BaseSpecification
import org.dozer.DozerBeanMapper

import static java.util.Arrays.asList
import static java.util.Collections.singletonList
import static org.cms.core.ConfigurationService.DOZER_MAPPER_SPEC
import static org.cms.core.user.Role.ROLE_ADMIN
import static org.cms.core.user.Role.ROLE_USER

class UserConverterTest extends BaseSpecification {

    def mapper = new DozerBeanMapper();
    def random = new Random();

    void setup() {
        mapper.setMappingFiles(singletonList(DOZER_MAPPER_SPEC));
    }

    def "Should map correctly to dto"() {
        given:
        User user = new User()
        user.id = random.nextLong();
        user.username = "aUsername";
        user.password = "aPassword";
        user.accountLocked = true;
        user.accountEnabled = true;
        user.accountExpired = true;
        user.credentialsExpired = true;

        UserRole adminRole = createUserRole(ROLE_ADMIN, user)
        UserRole userRole = createUserRole(ROLE_USER, user)
        user.roles.addAll(asList(adminRole, userRole))

        when:
        def dto = mapper.map(user, UserDto.class)

        then:
        def dtoRoles = dto.roles
        dtoRoles.contains(adminRole.role)
        dtoRoles.contains(userRole.role)
        dto.id == user.id
        dto.username == user.username
        dto.password == user.password
        dto.accountLocked == user.accountLocked
        dto.accountEnabled == user.accountEnabled
        dto.accountExpired == user.accountExpired
        dto.credentialsExpired == user.credentialsExpired
    }

    def "Should map correctly to user excluding roles"() {
        given:
        def dto = UserDto.builder()
                .username("username")
                .password("aPassword")
                .accountLocked(true)
                .accountEnabled(true)
                .accountExpired(true)
                .credentialsExpired(true)
                .id(random.nextLong())
                .roles(asList(ROLE_ADMIN, ROLE_USER))
                .build();

        when:
        def user = mapper.map(dto, User.class)

        then:
        user.id == dto.id
        user.username == dto.username
        user.password == dto.password
        user.accountLocked == dto.accountLocked
        user.accountEnabled == dto.accountEnabled
        user.accountExpired == dto.accountExpired
        user.credentialsExpired == dto.credentialsExpired
        user.roles.isEmpty()
    }

    def createUserRole(role, user) {
        def userRole = new UserRole();
        userRole.id = random.nextLong();
        userRole.role = role;
        userRole.user = user;
        return userRole
    }
}
