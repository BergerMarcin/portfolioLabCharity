package pl.coderslab.charity.services;


import pl.coderslab.charity.dtos.RoleDataDTO;

import java.util.List;

public interface RoleService {

    List<RoleDataDTO> findAll();

    RoleDataDTO findById (Long id);

}
