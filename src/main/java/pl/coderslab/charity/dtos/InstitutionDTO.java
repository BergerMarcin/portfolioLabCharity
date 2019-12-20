package pl.coderslab.charity.dtos;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class InstitutionDTO {

    @NotNull
    @Positive
    @Digits(integer = 4, fraction = 0)
    @UniqueElements
    private Long id;
    @NotBlank
    @Length(min = 3, max = 100)
    private String name;
    private String description;

}
