package pl.coderslab.charity.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter @Setter @ToString
public class InstitutionAddDataDTO {

    @NotBlank
    @Length(min = 3, max = 100)
    private String name;
    private String description;
    @NotNull
    private Boolean trusted;

}
