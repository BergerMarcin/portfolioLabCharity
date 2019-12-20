package pl.coderslab.charity.domain.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "institutions")
@Getter @Setter @ToString @EqualsAndHashCode(of = "id")
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private String description;

}
