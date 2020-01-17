package pl.coderslab.charity.services;

import pl.coderslab.charity.domain.entities.Role;

public interface RoleService {

    Role findAllByName (String name);

}
