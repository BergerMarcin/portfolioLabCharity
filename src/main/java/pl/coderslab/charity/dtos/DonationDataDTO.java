package pl.coderslab.charity.dtos;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;
import pl.coderslab.charity.domain.entities.Category;
import pl.coderslab.charity.domain.entities.Institution;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class DonationDataDTO {

    @Positive
    @Digits(integer = 4, fraction = 0)
    private Integer quantity;               // liczba worków
    @Size(min = 1)
    @UniqueElements
    private List<Category> categories;   // categories - lista obiektów typu Category
    @NotNull
    private Institution institution;        // institution - obiekt typu Institution
    @NotBlank
    @Length(min = 3)
    private String street;
    @NotBlank
    @Length(min = 3)
    private String city;
    private String zipCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    private LocalDate pickUpDate;
    //@DateTimeFormat(pattern = "hh:mm")
    private LocalTime pickUpTime;
    private String pickUpComment;

}
