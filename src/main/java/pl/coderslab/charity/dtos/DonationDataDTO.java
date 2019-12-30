package pl.coderslab.charity.dtos;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter @Setter @ToString
public class DonationDataDTO {

    @NotNull
    @Positive
    @Digits(integer = 4, fraction = 0)
    private Integer quantity;           // bags quantity/number
    @NotNull
    @Size(min = 1, max = 10)
    @UniqueElements
    private List<Long> categoryIds;     // chosen list of category id's
    @NotNull
    private Long institutionId;         // chosen institution id
    @NotBlank
    @Length(min = 3, max = 40)
    private String street;
    @NotBlank
    @Length(min = 3, max = 30)
    private String city;
    @Pattern(regexp = "[0-9][0-9]-[0-9][0-9][0-9]")
    private String zipCode;
    @NotBlank
    @Length(min = 9, max = 15)
    private String phone;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future
    private LocalDate pickUpDate;
    @NotNull
    //@DateTimeFormat(pattern = "hh:mm")
    private LocalTime pickUpTime;
    private String pickUpComment;

}
