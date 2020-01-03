package pl.coderslab.charity.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.UniqueElements;
import pl.coderslab.charity.validation.constraints.RepeatedPassword;

import javax.validation.constraints.*;

@Getter @Setter @ToString
public class RegistrationDataDTO {

    @NotBlank
    @Size(min = 2, max = 30)
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 40)
    private String lastName;
    @NotBlank @Email
    //TODO: setup it later with emails from MySQL table users: @UniqueElements
    private String email;
    @NotBlank @Size(min = 4, max = 12)
    private String password;
    @NotBlank @Size(min = 4, max = 12) @RepeatedPassword
    //TODO: setup it later with password: @RepeatedPassword
    private String rePassword;
    @NotNull
    @AssertTrue
    private Boolean termsAcceptance;

}

