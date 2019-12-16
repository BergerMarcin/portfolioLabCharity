package pl.coderslab.charity.domain.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "institutions")
@Data
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private String description;

}
