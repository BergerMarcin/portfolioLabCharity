package pl.coderslab.charity.domain.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

@Entity
@Table(name = "user_infos")
@Getter @Setter @ToString @EqualsAndHashCode(of = "id")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String street;
    @Column
    private String city;
    @Column
    private String zipCode;
    @Column
    private String phone;
    @Column
    private String pickUpComment;

    @CreationTimestamp
    @Column
    private java.sql.Timestamp created;
//    @UpdateTimestamp
//    @Column
//    private java.sql.Timestamp update;

}
