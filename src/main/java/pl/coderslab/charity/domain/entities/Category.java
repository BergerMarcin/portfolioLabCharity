package pl.coderslab.charity.domain.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "categories")
@Getter @Setter @ToString @EqualsAndHashCode(of = "id")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;

}
