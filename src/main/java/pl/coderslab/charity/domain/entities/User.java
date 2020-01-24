package pl.coderslab.charity.domain.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// Field active is just to inactive user but not to cancel it
// Change of active of ROLE_USER is possible only if ROLE_ADMIN
// Change of active of ROLE_ADMIN is possible only if ROLE_SUPERADMIN
@Entity
@Table(name = "users")
@Getter @Setter @ToString(exclude = "password", callSuper = true) @EqualsAndHashCode(of = "id")
public class User extends BaseEntity {

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    @Column(nullable = false)
    private Boolean active = Boolean.FALSE;

    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "roles_id"))
//    Only List operates well (set causes problems with adding new records)
    private List<Role> roles = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
//    @JoinTable(name = "users_user_infos", joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_infos_id"))
//    @JoinColumn(name = "user_info_id", unique = true)
    private UserInfo userInfo;

}
