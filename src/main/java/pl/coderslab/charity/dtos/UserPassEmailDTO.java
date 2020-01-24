package pl.coderslab.charity.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.ScriptAssert;
import pl.coderslab.charity.validation.constraints.EqualTwoFields;
import pl.coderslab.charity.validation.constraints.UniqueEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter @ToString
//        (exclude = {"password", "rePassword"})
@EqualsAndHashCode(of = "id")
@ScriptAssert(lang = "javascript", script = "_this.email.equals(_this.reEmail)", reportOn = "reEmail")
@EqualTwoFields(baseField = "password", matchField = "rePassword", reportOn = "rePassword")
public class UserPassEmailDTO {

    private Long id;
    @NotBlank @Email @UniqueEmail
    private String email;
    @NotBlank @Email @UniqueEmail
    private String reEmail;
    @Size(min = 4, max = 12)
    private String password;
    @Size(min = 4, max = 12)
    private String rePassword;

}
