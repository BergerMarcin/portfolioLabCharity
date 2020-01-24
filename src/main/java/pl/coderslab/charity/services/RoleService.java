package pl.coderslab.charity.services;


import pl.coderslab.charity.dtos.RoleDTO;

import java.util.List;

/**
 * ROLE_SUPERADMIN choice is hidden at adding/creating and update admin
 * ROLE_SUPERADMIN may be set only from MySQL console
 * ROLE_SUPERADMIN allows (additionally to ROLE_ADMIN) from application admin panel to:
 *  - delete admin
 *  - change admin's password and email
 */
public interface RoleService {

    List<RoleDTO> findAll();

    RoleDTO findById (Long id);

}
