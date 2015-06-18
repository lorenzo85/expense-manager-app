package org.cms.service.user;

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
    Long id;
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
    Set<UserAuthority> authorities = new HashSet<>();

    public void revokeRole(UserRole role) {
        checkNotNull(role);
        for (Iterator<UserAuthority> iterator = authorities.iterator(); iterator.hasNext();) {
            UserAuthority authority = iterator.next();
            if (authority.hasRole(role)) {
                iterator.remove();
            }
        }
    }

    public void grantRole(UserRole role) {
        checkNotNull(authorities);
        authorities.add(new UserAuthority(this, role));
    }
}