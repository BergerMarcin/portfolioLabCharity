package pl.coderslab.charity.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.domain.entities.Role;
import pl.coderslab.charity.domain.repositories.RoleRepository;
import pl.coderslab.charity.dtos.RoleDTO;
import pl.coderslab.charity.services.Mapper;
import pl.coderslab.charity.services.RoleService;

import java.util.List;

/**
 * ROLE_SUPERADMIN choice is hidden at adding/creating and update admin
 * ROLE_SUPERADMIN may be set only from MySQL console
 * ROLE_SUPERADMIN allows (additionally to ROLE_ADMIN) from application admin panel to:
 *  - delete admin
 *  - change admin's password and email
 */
@Service
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleDTO> findAll() {
        List<Role> roleList = roleRepository.findAll();
        // remove/hidden ROLE_SUPERADMIN
        for (Role role: roleList) {}
        Mapper<Role, RoleDTO> mapper = new Mapper();
        return mapper.mapList(roleList, new RoleDTO(), "STANDARD");
    }

    @Override
    public RoleDTO findById(Long id) {
        Role role = roleRepository.findAllById(id);
        Mapper<Role, RoleDTO> mapper = new Mapper();
        return mapper.mapObj(role, new RoleDTO(), "STANDARD");
    }

}
