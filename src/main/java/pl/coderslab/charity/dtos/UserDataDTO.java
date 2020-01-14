package pl.coderslab.charity.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.coderslab.charity.domain.entities.BaseEntity;
import pl.coderslab.charity.domain.entities.Role;
import pl.coderslab.charity.domain.entities.UserInfo;
import pl.coderslab.charity.validation.constraints.UniqueEmail;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString(exclude = "password") @EqualsAndHashCode(of = "id")
public class UserDataDTO {

    @NotNull @Positive @Digits(integer = 4, fraction = 0)
    private Long id;
    @NotBlank @Size(min = 2, max = 30)
    private String firstName;
    @NotBlank @Size(min = 2, max = 40)
    private String lastName;
    @NotBlank @Email @UniqueEmail
    private String email;
    @NotBlank @Size(min = 4, max = 12)
    private String password;
    @NotBlank
    private Boolean active;

    //TODO: check if ROLE is needed
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();
    //?????????
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_info_id", unique = true)
    private UserInfo userInfo;

}
