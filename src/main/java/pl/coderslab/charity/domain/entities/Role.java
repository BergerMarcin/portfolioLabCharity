package pl.coderslab.charity.domain.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@Getter @Setter @ToString(callSuper = true) @EqualsAndHashCode(of = "id")
public class Role extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

}
