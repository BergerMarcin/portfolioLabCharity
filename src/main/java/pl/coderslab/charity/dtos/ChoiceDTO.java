package pl.coderslab.charity.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter @Setter @ToString(exclude = {"choice", "id", "email"}) @EqualsAndHashCode(of = {"id", "email"})
public class ChoiceDTO {

    @NotNull
    private Integer choice;
    //    @NotNull @Positive @Digits(integer = 4, fraction = 0)
    private Long id;
    @Email
    private String email;

}
