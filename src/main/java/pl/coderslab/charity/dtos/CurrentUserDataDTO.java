package pl.coderslab.charity.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import pl.coderslab.charity.domain.entities.Role;
import pl.coderslab.charity.validation.constraints.UniqueEmail;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
public class CurrentUserDataDTO {

    @NotBlank
    @Size(min = 2, max = 30)
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 40)
    private String lastName;
    @NotBlank @Email
    @UniqueEmail
    private String email;
    @NotNull
    private Set<Role> roles = new HashSet<>();
    @NotBlank
    @Length(min = 3, max = 40)
    private String street;
    @NotBlank
    @Length(min = 3, max = 30)
    private String city;
    @Pattern(regexp = "[0-9][0-9]-[0-9][0-9][0-9]")
    private String zipCode;
    @NotBlank
    @Length(min = 9, max = 15)
    private String phone;
    private String pickUpComment;

}
