package pl.coderslab.charity.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.domain.entities.Role;
import pl.coderslab.charity.domain.repositories.RoleRepository;
import pl.coderslab.charity.dtos.RoleDataDTO;
import pl.coderslab.charity.services.RoleService;

import java.util.ArrayList;
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
        List<RoleDataDTO> roleDataDTOList = new ArrayList<>();
        for (Role role: roleList) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
            RoleDataDTO roleDataDTO = modelMapper.map(role, RoleDataDTO.class);
            roleDataDTOList.add(roleDataDTO);
        }
        return roleDataDTOList;
    }

//    @Override
//    public Role findAllByName(String name) {
//        return roleRepository.findAllByName(name);
//    }

}
