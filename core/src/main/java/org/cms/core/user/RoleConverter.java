package org.cms.core.user;

import org.dozer.CustomConverter;

import java.util.Set;
import java.util.stream.Collectors;

public class RoleConverter implements CustomConverter {

    @SuppressWarnings("unchecked")
    @Override
    public Object convert(Object destination, Object source, Class destClass, Class sourceClass) {
        Set<UserRole> rolesSet = (Set<UserRole>) source;
        return rolesSet
                .stream()
                .map(userRole -> userRole.role)
                .collect(Collectors.toList());
    }

}
