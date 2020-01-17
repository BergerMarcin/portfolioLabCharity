package pl.coderslab.charity.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

@Getter @Setter @ToString @EqualsAndHashCode(of = "id")
public class RoleDataDTO {

    @NotNull @Positive @Digits(integer = 4, fraction = 0)
    private Long id;
    @NotBlank @Pattern(regexp = "ROLE_[A-Z]{3,}")
    private String name;

}
