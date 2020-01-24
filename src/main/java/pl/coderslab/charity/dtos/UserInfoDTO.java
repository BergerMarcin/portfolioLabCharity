package pl.coderslab.charity.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Getter @Setter @ToString @EqualsAndHashCode(of = "id")
public class UserInfoDTO {

//    @NotNull @Positive @Digits(integer = 4, fraction = 0)
    private Long id;
    @Length(min = 3, max = 40)
    private String street;
    @Length(min = 3, max = 30)
    private String city;
    @Pattern(regexp = "[0-9][0-9]-[0-9][0-9][0-9]")
    private String zipCode;
    @Length(min = 9, max = 15)
    private String phone;
    private String pickUpComment;
}
