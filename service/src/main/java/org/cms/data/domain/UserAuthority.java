package org.cms.data.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="user_authority", uniqueConstraints = @UniqueConstraint(columnNames = {"role", "user_id"}))
public class UserAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public UserAuthority() {
    }

    public UserAuthority(User user, UserRole role) {
        this.user = user;
        this.role = role;
    }
}
