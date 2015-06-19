package org.cms.service.user;

import com.google.common.base.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Table(name="user_role", uniqueConstraints = @UniqueConstraint(columnNames = {"role", "user_id"}))
public class UserRole {
    @Id
    @GeneratedValue(strategy = AUTO)
    Long id;
    @NotNull
    @ManyToOne(fetch = LAZY)
    User user;
    @NotNull
    @Enumerated(STRING)
    Role role;

    public UserRole() {
    }

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public boolean hasRole(Role role) {
        return this.role.equals(role);
    }

    public Role getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole that = (UserRole) o;
        return Objects.equal(user, that.user) &&
                Objects.equal(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(user, role);
    }
}
