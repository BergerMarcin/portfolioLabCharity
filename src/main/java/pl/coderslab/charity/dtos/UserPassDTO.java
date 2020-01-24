package pl.coderslab.charity.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.coderslab.charity.validation.constraints.EqualTwoFields;

import javax.validation.constraints.Size;

@Getter @Setter @ToString
//        (exclude = {"password", "rePassword"})
@EqualsAndHashCode(of = "id")
@EqualTwoFields(baseField = "password", matchField = "rePassword", reportOn = "rePassword")
public class UserPassDTO {

    // id of the user to be deleted (DTO for deleting user or admin)
    private Long id;
    @Size(min = 4, max = 12)
    private String password;
    @Size(min = 4, max = 12)
    private String rePassword;

}
