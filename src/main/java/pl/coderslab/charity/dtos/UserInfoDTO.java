package pl.coderslab.charity.dtos;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

public class UserInfoDTO {

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
