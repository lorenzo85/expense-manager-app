package org.cms.core.user;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name="user", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User {
    @Id
    @GeneratedValue(strategy = AUTO)
    long id;
    @NotNull
    @Size(min = 1, max = 50)
    String username;
    @NotNull
    @Size(min = 1, max = 100)
    String password;
    @NotNull
    boolean accountLocked;
    @NotNull
    boolean accountExpired;
    @NotNull
    boolean accountEnabled;
    @NotNull
    boolean credentialsExpired;
    @OneToMany(cascade = ALL, mappedBy = "user", fetch = EAGER, orphanRemoval = true)
    Set<UserRole> roles = new HashSet<>();

    public void revokeRole(Role roleToRevoke) {
        checkNotNull(roleToRevoke);
        for (Iterator<UserRole> iterator = roles.iterator(); iterator.hasNext();) {
            UserRole role = iterator.next();
            if (role.sameAs(roleToRevoke)) {
                iterator.remove();
            }
        }
    }

    public void grantRole(Role roleToGrant) {
        checkNotNull(roles);
        roles.add(new UserRole(this, roleToGrant));
    }

}