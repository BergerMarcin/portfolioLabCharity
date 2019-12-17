package pl.coderslab.charity.dtos;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;
import pl.coderslab.charity.domain.entities.Category;
import pl.coderslab.charity.domain.entities.Institution;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class DonationDataDTO {

    @Positive
    @Digits(integer = 4, fraction = 0)
    private Integer quantity;               // liczba worków
    @Size(min = 1)
    @UniqueElements
    private List<Category> categories = new ArrayList();   // categories - lista obiektów typu Category
    @NotNull
    private Institution institution;        // institution - obiekt typu Institution
    @NotBlank
    @Length(min = 3)
    private String street;
    @NotBlank
    @Length(min = 3)
    private String city;
    private String zipCode;
    @NotEmpty
    @FutureOrPresent
    private LocalDate pickUpDate;
    @NotEmpty
    private LocalTime pickUpTime;
    private String pickUpComment;

}
