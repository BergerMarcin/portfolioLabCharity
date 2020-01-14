package pl.coderslab.charity.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import pl.coderslab.charity.domain.entities.BaseEntity;
import pl.coderslab.charity.domain.entities.Role;
import pl.coderslab.charity.domain.entities.UserInfo;
import pl.coderslab.charity.validation.constraints.UniqueEmail;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString(exclude = "roleDataDTOList") @EqualsAndHashCode(of = "id")
public class UserDataDTO {

    @NotNull @Positive @Digits(integer = 4, fraction = 0)
    private Long id;
    @NotBlank @Size(min = 2, max = 30)
    private String firstName;
    @NotBlank @Size(min = 2, max = 40)
    private String lastName;
    @NotBlank @Email @UniqueEmail
    private String email;
    @NotNull
    private Boolean active = Boolean.FALSE;
    @NotNull
    private List<RoleDataDTO> roleDataDTOList = new ArrayList<>();
    private UserInfoDTO userInfoDTO;

}
