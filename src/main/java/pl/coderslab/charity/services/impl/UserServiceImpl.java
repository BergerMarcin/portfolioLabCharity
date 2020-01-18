package pl.coderslab.charity.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.domain.entities.Institution;
import pl.coderslab.charity.domain.entities.Role;
import pl.coderslab.charity.domain.entities.User;
import pl.coderslab.charity.domain.repositories.RoleRepository;
import pl.coderslab.charity.domain.repositories.UserRepository;
import pl.coderslab.charity.dtos.RoleDataDTO;
import pl.coderslab.charity.dtos.UserDataDTO;
import pl.coderslab.charity.exceptions.EntityToDataBaseException;
import pl.coderslab.charity.services.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Boolean isEmailUnique(String email) {
        return userRepository.findAllByEmail(email) == null;
    }

    @Override
    public List<UserDataDTO> findAllByRoleName (String roleName) {
        Role role = roleRepository.findAllByName(roleName);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllByRoleName role: {}", role.toString());
        List<User> userList = userRepository.findAllByRoles(role);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllByRoleName userList: {}", userList.toString());
        List<UserDataDTO> userDataDTOList = new ArrayList<>();
        for (User user: userList) {
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            UserDataDTO userDataDTO = modelMapper.map(user, UserDataDTO.class);
            // Role list mapping manually (ModelMapper does not map)
            List<RoleDataDTO> roleDataDTOList = new ArrayList<>();
            for (Role r: user.getRoles()) {
                ModelMapper modelMapper1 = new ModelMapper();
                modelMapper1.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
                RoleDataDTO roleDataDTO = modelMapper.map(r, RoleDataDTO.class);
                roleDataDTOList.add(roleDataDTO);
            }
            userDataDTO.setRoleDataDTOList(roleDataDTOList);
            userDataDTOList.add(userDataDTO);
            log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllByRoleName userDataDTO: {}", userDataDTO.toString());
            log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllByRoleName userDataDTO.getRoleDataDTOList: {}", userDataDTO.getRoleDataDTOList());
            log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllByRoleName userDataDTO.getUserInfoDTO: {}", userDataDTO.getUserInfoDataDTO());
        }
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllByRoleName userDataDTOList: {}", userDataDTOList.toString());
        return userDataDTOList;
    }

    @Override
    public UserDataDTO findAllById(Long id) {
        User user = userRepository.findAllById(id);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllById user: {}", user.toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllById user.getRoleDataDTOList: {}", user.getRoles().toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllById user.getUserInfoDTO: {}", user.getUserInfo().toString());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        UserDataDTO userDataDTO = modelMapper.map(user, UserDataDTO.class);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllById userDataDTO: {}", userDataDTO.toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllById userDataDTO.getRoleDataDTOList: {}", userDataDTO.getRoleDataDTOList().toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllById userDataDTO.getUserInfoDTO: {}", userDataDTO.getUserInfoDataDTO().toString());
        return userDataDTO;
    }

    //    @Override
//    public User findAllByEmail(String email) {
//        return userRepository.findAllByEmail(email);
//    }

//    @Override
//    public User findAllWithUserInfoByEmail(String email) {
//        return userRepository.findAllWithUserInfoByEmail(email);
//    }

    @Override
    public void saveUser(UserDataDTO userDataDTO) throws EntityToDataBaseException {

        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! saveUser !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! UserServiceImpl.saveUser userDataDTO to be mapped to User: {}", userDataDTO.toString());

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        User user = modelMapper.map(userDataDTO, User.class);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! UserServiceImpl.saveUser user (from userDataDTO) after simple mapping: {}", user.toString());
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! UserServiceImpl.saveUser user.userInfo (from userDataDTO) after simple mapping: {}", user.getUserInfo().toString());
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! UserServiceImpl.saveUser user.roles (from userDataDTO) after simple mapping: {}", user.getRoles().toString());

        // Protection against unauthorised in fact update (instead of save=add new record/line)
        if (user.getId() != null) {
            throw new EntityToDataBaseException("Wystąpił błąd przy walidacji lub zapisie danych. Powtórz całą operację");
        }

        // Fill-in roles of user
        List<Role> roleList = new ArrayList<>();
        for (RoleDataDTO roleDataDTO: userDataDTO.getRoleDataDTOList()) {
            ModelMapper modelMapper2 = new ModelMapper();
            modelMapper2.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
            Role role = modelMapper2.map(roleDataDTO, Role.class);
            roleList.add(role);
        }
        user.setRoles(roleList);

        // Encoding password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Final saving user
        User userSaved = userRepository.save(user);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! UserServiceImpl.saveUser userSaved: {}", userSaved.toString());
        // check if saved
        if (userSaved == null) {
            throw new EntityToDataBaseException("Wystąpił błąd przy walidacji lub zapisie danych. Powtórz całą operację");
        }


    }
}
