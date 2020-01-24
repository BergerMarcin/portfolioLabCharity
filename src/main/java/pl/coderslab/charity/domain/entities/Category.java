package pl.coderslab.charity.domain.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "categories")
@Getter @Setter @ToString(callSuper = true) @EqualsAndHashCode(of = "id")
public class Category extends BaseEntity {

    @Column
    private String name;

}
