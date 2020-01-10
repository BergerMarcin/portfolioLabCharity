package pl.coderslab.charity.dtos;

import lombok.Data;
import pl.coderslab.charity.domain.entities.Role;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
public class AdminRoleChoiceDataDTO {

    @NotBlank
    private Set<Role> roles = new HashSet<>();

}
