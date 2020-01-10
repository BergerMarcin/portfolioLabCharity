package pl.coderslab.charity.dtos;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import pl.coderslab.charity.domain.entities.Role;
import pl.coderslab.charity.validation.constraints.UniqueEmail;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Data
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
    @Column(nullable = false)
    private Boolean active = Boolean.FALSE;
    @NotNull
    private Set<Role> roles = new HashSet<>();
    @NotBlank
    // "Manual" choice of role. Especially important in case multiple roles (i.e. ADMIN) - based on that chosen view
    // If ROLE_USER not found should be reported to ADMIN
    private String chosenRole;
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
