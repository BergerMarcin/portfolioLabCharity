package pl.coderslab.charity.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.coderslab.charity.validation.constraints.EqualTwoFields;
import pl.coderslab.charity.validation.constraints.UniqueEmail;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;


@Getter @Setter @ToString
//        (exclude = {"password", "rePassword"})
@EqualsAndHashCode(of = "id")
@EqualTwoFields(baseField = "password", matchField = "rePassword", reportOn = "rePassword")
public class UserDTO {

    //    @NotNull @Positive @Digits(integer = 4, fraction = 0)
    private Long id;
    @Size(min = 2, max = 30)
    private String firstName;
    @Size(min = 2, max = 40)
    private String lastName;
    @NotBlank @Email @UniqueEmail
    private String email;
    @Size(min = 4, max = 12)
    private String password;
    @Size(min = 4, max = 12)
    private String rePassword;
    @AssertTrue
    private Boolean termsAcceptance;
    @NotNull
    private Boolean active = Boolean.FALSE;
    @Valid
    private UserInfoDTO userInfoDTO = new UserInfoDTO();
    @Valid
    private List<RoleDTO> roleDTOList = new ArrayList<>();

}



