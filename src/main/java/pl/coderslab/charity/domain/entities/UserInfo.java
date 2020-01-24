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
@Getter @Setter @ToString(callSuper = true) @EqualsAndHashCode(of = "id")
public class UserInfo extends BaseEntity {

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

}
