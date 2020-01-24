package pl.coderslab.charity.domain.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ROLE_SUPERADMIN choice is hidden at adding/creating and update admin
 * ROLE_SUPERADMIN may be set only from MySQL console
 * ROLE_SUPERADMIN allows (additionally to ROLE_ADMIN) from application admin panel to:
 *  - delete admin
 *  - change admin's password and email
 *
 * Role lines:
 * INSERT INTO roles (id, name) VALUES (1, 'ROLE_USER');
 * INSERT INTO roles (id, name) VALUES (2, 'ROLE_ADMIN');
 * INSERT INTO roles (id, name) VALUES (3, 'ROLE_SUPERADMIN');
 */
@Entity
@Table(name = "roles")
@Getter @Setter @ToString(callSuper = true) @EqualsAndHashCode(of = "id")
public class Role extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

}
