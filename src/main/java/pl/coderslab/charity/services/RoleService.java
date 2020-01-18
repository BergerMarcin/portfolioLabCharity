package pl.coderslab.charity.services;

import pl.coderslab.charity.domain.entities.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll ();

    Role findAllByName (String name);

}
