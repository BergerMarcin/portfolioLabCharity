package pl.coderslab.charity.services;

import pl.coderslab.charity.domain.entities.Role;
import pl.coderslab.charity.dtos.RoleDataDTO;

import java.util.List;

public interface RoleService {

//    List<Role> findAll ();

    List<RoleDataDTO> findAllMapToListOfRoleDataDTO();

//    Role findAllByName (String name);

}
