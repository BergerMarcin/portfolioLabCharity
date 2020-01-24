package pl.coderslab.charity.domain.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "donations")
@Getter @Setter @ToString(exclude = "categories", callSuper = true) @EqualsAndHashCode(of = "id")
public class Donation extends BaseEntity {

    @Column
    private Integer quantity;               // liczba worków
    @ManyToMany
    @JoinColumn (name = "categories_id")
    private List<Category> categories = new ArrayList<>();   // categories - lista obiektów typu Category
    @ManyToOne
    @JoinColumn (name = "institution_id")
    private Institution institution;        // institution - obiekt typu Institution
    @Column
    private String street;
    @Column
    private String city;
    @Column
    private String zipCode;
    @Column
    private String phone;
    @Column(nullable = false)
    private LocalDate pickUpDate;
    @Column(nullable = false)
    private LocalTime pickUpTime;
    @Column
    private String pickUpComment;

    @ManyToOne
//    @JoinColumn (name = "user_id")
    private User user;

}
