package pl.coderslab.charity.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.ScriptAssert;
import pl.coderslab.charity.validation.constraints.EqualTwoFields;
import pl.coderslab.charity.validation.constraints.UniqueEmail;

import javax.validation.constraints.*;

// _this means the class/method that the annotation refer to (is above)
//@ScriptAssert(lang = "javascript", script = "_this.password.equals(_this.rePassword)", reportOn = "rePassword")
@EqualTwoFields(baseField = "password", matchField = "rePassword", reportOn = "rePassword")
@Getter @Setter @ToString(exclude = {"password", "rePassword"}) @EqualsAndHashCode(of = "email")
public class RegistrationDTO {

    @NotBlank
    @Size(min = 2, max = 30)
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 40)
    private String lastName;
    @NotBlank @Email @UniqueEmail
    private String email;
    @NotBlank @Size(min = 4, max = 12)
    private String password;
    @NotBlank @Size(min = 4, max = 12)
    private String rePassword;
    @NotNull @AssertTrue
    private Boolean termsAcceptance;

}

