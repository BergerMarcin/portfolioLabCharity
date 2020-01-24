package pl.coderslab.charity.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.coderslab.charity.dtos.RoleDTO;
import pl.coderslab.charity.services.RoleService;

// Trzeba wybrać implementacje interfejsu Converter z converter.spring.core.converter
public class RoleDTOConverter implements Converter<String, RoleDTO> {

    private RoleService roleService;

    // Powołanie roleService przez settera (a nie konstruktora) bo musimy w AppConfigu powołać ten
    // converter, a ten converter do powołania w AppConfig wymaga tam znów publisherDao, a
    // trudno=głupio powoływać publisherDao w AppConfig)
    @Autowired
    public void setRoleService (RoleService roleService) {
        this.roleService = roleService;
    }

    // Zamienia String przesyłany za pomocą GET/POST? na id i sprawdza czy występuje Publisher o danym id
    // W przypadku problemów rzuca wyjątkiem
    @Override
    public RoleDTO convert(String source) {
        try {
            Long id = Long.parseLong(source);
            RoleDTO roleDTO = roleService.findById(id);
            if (roleDTO == null) {
                throw new IllegalArgumentException("There is no role of id: " + id);
            }
            return roleDTO;
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Value " + source + " is not valid for role");
        }
    }

}