package pl.coderslab.charity.domain.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "donations")
@Getter @Setter @ToString(exclude = "categories") @EqualsAndHashCode
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Integer quantity;               // liczba worków
    @ManyToMany
    private List<Category> categories = new ArrayList();   // categories - lista obiektów typu Category
    @ManyToOne
    @JoinColumn (name = "institution_id")
    private Institution institution;        // institution - obiekt typu Institution
    @Column
    private String street;
    @Column
    private String city;
    @Column
    private String zipCode;
    @Column(nullable = false)
    private LocalDate pickUpDate;
    @Column(nullable = false)
    private LocalTime pickUpTime;
    @Column
    private String pickUpComment;

}
