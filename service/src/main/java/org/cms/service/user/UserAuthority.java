package org.cms.service.user;

import com.google.common.base.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.EnumType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Table(name="user_authority", uniqueConstraints = @UniqueConstraint(columnNames = {"role", "user_id"}))
public class UserAuthority {
    @Id
    @GeneratedValue(strategy = AUTO)
    Long id;
    @NotNull
    @ManyToOne(fetch = LAZY)
    User user;
    @NotNull
    @Enumerated(STRING)
    UserRole role;

    public UserAuthority() {
    }

    public UserAuthority(User user, UserRole role) {
        this.user = user;
        this.role = role;
    }

    public boolean hasRole(UserRole role) {
        return this.role.equals(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAuthority that = (UserAuthority) o;
        return Objects.equal(user, that.user) &&
                Objects.equal(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(user, role);
    }
}
