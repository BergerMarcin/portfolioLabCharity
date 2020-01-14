package pl.coderslab.charity.dtos;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import pl.coderslab.charity.domain.entities.Role;
import pl.coderslab.charity.validation.constraints.UniqueEmail;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

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
    @NotNull
    private Boolean active = Boolean.FALSE;
    @NotNull
    private List<Role> roles = new ArrayList<>();
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
