package pl.coderslab.charity.domain.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "institutions")
@Getter @Setter @ToString(callSuper = true) @EqualsAndHashCode(of = "id")
public class Institution extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private String description;

}
