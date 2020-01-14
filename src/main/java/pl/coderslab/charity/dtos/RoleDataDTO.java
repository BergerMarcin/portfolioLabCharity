package pl.coderslab.charity.dtos;

import javax.validation.constraints.*;

public class RoleDataDTO {

    @NotNull @Positive @Digits(integer = 4, fraction = 0)
    private Long id;
    @NotBlank @Pattern(regexp = "ROLE_[A-Z]{3,}")
    private String name;

}
