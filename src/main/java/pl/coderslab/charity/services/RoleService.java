package pl.coderslab.charity.services;


import pl.coderslab.charity.dtos.RoleDTO;

import java.util.List;

public interface RoleService {

    List<RoleDTO> findAll();

    RoleDTO findById (Long id);

}
