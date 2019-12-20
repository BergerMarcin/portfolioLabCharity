package pl.coderslab.charity.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter @Setter @ToString
public class CategoryDTO {

    @NotNull
    @Positive
    @Digits(integer = 4, fraction = 0)
    @UniqueElements
    private Long id;
    @NotBlank
    @Length(min = 3, max = 40)
    private String name;

}
