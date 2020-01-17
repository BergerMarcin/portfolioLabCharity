package pl.coderslab.charity.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Getter @Setter @ToString @EqualsAndHashCode(of = "id")
public class UserInfoDataDTO {

    @NotNull @Positive @Digits(integer = 4, fraction = 0)
    private Long id;
    @NotBlank @Length(min = 3, max = 40)
    private String street;
    @NotBlank @Length(min = 3, max = 30)
    private String city;
    @Pattern(regexp = "[0-9][0-9]-[0-9][0-9][0-9]")
    private String zipCode;
    @NotBlank @Length(min = 9, max = 15)
    private String phone;
    private String pickUpComment;
}
