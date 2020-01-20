package pl.coderslab.charity.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.domain.entities.Role;
import pl.coderslab.charity.domain.repositories.RoleRepository;
import pl.coderslab.charity.dtos.RoleDataDTO;
import pl.coderslab.charity.services.Mapper;
import pl.coderslab.charity.services.RoleService;

import java.util.List;

@Service
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

//    @Override
//    public List<Role> findAll() { return roleRepository.findAll(); }

    @Override
    public List<RoleDataDTO> findAllMapToListOfRoleDataDTO() {
        List<Role> roleList = roleRepository.findAll();
        Mapper<Role, RoleDataDTO> mapper = new Mapper();
        return mapper.mapList(roleList, new RoleDataDTO(), "STANDARD");
    }

//    @Override
//    public Role findAllByName(String name) {
//        return roleRepository.findAllByName(name);
//    }

}