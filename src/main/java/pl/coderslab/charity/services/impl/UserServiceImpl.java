package pl.coderslab.charity.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.domain.entities.Role;
import pl.coderslab.charity.domain.entities.User;
import pl.coderslab.charity.domain.repositories.RoleRepository;
import pl.coderslab.charity.domain.repositories.UserRepository;
import pl.coderslab.charity.dtos.RoleDTO;
import pl.coderslab.charity.dtos.UserDTO;
import pl.coderslab.charity.exceptions.EntityToDataBaseException;
import pl.coderslab.charity.services.Mapper;
import pl.coderslab.charity.services.UserService;

import java.util.ArrayList;
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
    public List<UserDTO> findAllByRoleName (String roleName) {
        Role role = roleRepository.findAllByName(roleName);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllByRoleName role: {}", role.toString());
        List<User> userList = userRepository.findAllByRoles(role);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllByRoleName userList: {}", userList.toString());

        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user: userList) {
            UserDTO userDTO = mapUserToUserDTO(user);
            userDTOList.add(userDTO);
            log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllByRoleName userDTO: {}", userDTO.toString());
            log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllByRoleName userDTO.getRoleDTOList: {}", userDTO.getRoleDTOList());
            log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllByRoleName userDTO.getUserInfoDTO: {}", userDTO.getUserInfoDTO());
        }
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllByRoleName userDTOList: {}", userDTOList.toString());
        return userDTOList;
    }

    @Override
    public UserDTO findById(Long id) {
        User user = userRepository.findAllById(id);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllById user: {}", user.toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllById user.getRoleDTOList: {}", user.getRoles().toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllById user.getUserInfoDTO: {}", user.getUserInfo());
        UserDTO userDTO = mapUserToUserDTO(user);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllById userDTO: {}", userDTO.toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllById userDTO.getRoleDTOList: {}", userDTO.getRoleDTOList().toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllById userDTO.getUserInfoDTO: {}", userDTO.getUserInfoDTO());
        return userDTO;
    }

    @Override
    public void saveNewUser(UserDTO userDTO) throws EntityToDataBaseException {
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! saveUser !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! UserServiceImpl.saveUser userDTO to be mapped to User: {}", userDTO.toString());

        User user = mapUserDTOToUser(userDTO);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! UserServiceImpl.saveUser user (from userDTO) after simple mapping: {}", user.toString());
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! UserServiceImpl.saveUser user.userInfo (from userDTO) after simple mapping: {}", user.getUserInfo().toString());
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! UserServiceImpl.saveUser user.roles (from userDTO) after simple mapping: {}", user.getRoles().toString());

        // Protection against unauthorised in fact update (instead of save=add new record/line)
        if (user.getId() != null) {
            throw new EntityToDataBaseException("Wystąpił błąd przy walidacji lub zapisie danych. Powtórz całą operację");
        }

        // Encoding password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Final saving user
        User userSaved = userRepository.save(user);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! UserServiceImpl.saveUser userSaved: {}", userSaved.toString());
        // check if saved
        if (userSaved == null) {
            throw new EntityToDataBaseException("Wystąpił błąd przy walidacji lub zapisie danych. Powtórz całą operację");
        }

        //TODO: send an email informing about add as roles.name
    }

    @Override
    public void saveNewAdmin(UserDTO userDTO, Boolean roleUser) throws EntityToDataBaseException {
        // Set roleList (ROLE_ADMIN + ROLE_USER if roleUser) and then mapping it to roleDTOList.
        // Then roleDTOList is put into userDTO (with all new admin data) to be finally saved with saveUser method
        List<Role> roleList = new ArrayList<>();
        roleList.add(roleRepository.findAllByName("ROLE_ADMIN"));
        if (roleUser) {
            roleList.add(roleRepository.findAllByName("ROLE_USER"));
        }
        Mapper<Role, RoleDTO> mapper = new Mapper<>();
        List<RoleDTO> roleDTOList = mapper.mapList(roleList, new RoleDTO(),"STANDARD");
        if (roleDTOList == null) {
            throw new EntityToDataBaseException("Wystąpił błąd przy walidacji lub zapisie danych. Powtórz całą operację");
        }
        userDTO.setRoleDTOList(roleDTOList);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.addAdmin userDTO: {}", userDTO.toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.addAdmin userDTO.roleDTOList: {}", userDTO.getRoleDTOList().toString());

        // saving Admin (userDTO) as each User. Exception is coming from method saveUser
        saveNewUser(userDTO);
    }

    @Override
    public void updateAdmin(Long idProtected, UserDTO userDTO) throws EntityToDataBaseException {
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! updateAdmin !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! UserServiceImpl.updateAdmin userDTO to be mapped to User: {}", userDTO.toString());
        User oldUser = userRepository.findAllById(idProtected);

        User user =  mapUserDTOToUser(userDTO);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! UserServiceImpl.updateAdmin user (from userDTO) after simple mapping: {}", user.toString());

        // Protection against unauthorised in fact update another record/line (instead of update the right one record/line)
        if (user.getId() == null || user.getId() != idProtected) {
            throw new EntityToDataBaseException("Wystąpił błąd przy walidacji lub zapisie danych. Powtórz całą operację");
        }

        // Checking password old with new-given-to-form
        if (!passwordEncoder.matches(user.getPassword(), oldUser.getPassword())) {
            throw new EntityToDataBaseException("Niepoprawne hasło. Powtórz całą operację");
        }

        // Final preparing data to be saved
        // Encoding password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // set old email from DB (because there was set x@x.xxx email to cheat @UniqueEmail validator)
        user.setEmail(oldUser.getEmail());
        // set old creation date
        user.setCreatedOn(oldUser.getCreatedOn());
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! UserServiceImpl.updateAdmin user to be saved: {}", user.toString());

        // Final update Admin
        User userSaved = userRepository.save(user);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! UserServiceImpl.updateAdmin userSaved saved: {}", userSaved.toString());
        // check if update succeed
        if (userSaved == null || user.getId() != userSaved.getId()) {
            throw new EntityToDataBaseException("Wystąpił błąd przy walidacji lub zapisie danych. Powtórz całą operację");
        }

        //TODO: send an email informing about changes to old and new email.
        // email to both (both as unauthorised change might be done, so both emails should be informed): {oldUser.getEmail(), user.getEmail()}
    }


    /**
     * Separate method to map UserDTO to User
     * (due to issue mapping field with nested object RoleDTO to Role)
     * @param userDTO
     * @return
     */
    private User mapUserDTOToUser(UserDTO userDTO) {
        // Mapping UserDTO to User (issue with RoleDTO, so below mapping separately)
        Mapper<UserDTO, User> mapper1 = new Mapper();
        User user = mapper1.mapObj(userDTO, new User(),"STANDARD");
        // Mapping list of RoleDTO and set it to User
        Mapper<RoleDTO, Role> mapper2 = new Mapper();
        user.setRoles(mapper2.mapList(userDTO.getRoleDTOList(), new Role(),"STANDARD"));
        return user;
    }

    /**
     * Separate method to map User to UserDTO
     * (due to issue mapping field with nested object Role to RoleDTO)
     * @param user
     * @return
     */
    private UserDTO mapUserToUserDTO(User user) {
        // Mapping User to UserDTO (below maps UserInfoDTO but does not RoleDTO)
        Mapper<User, UserDTO> mapper1 = new Mapper<>();
        UserDTO userDTO = mapper1.mapObj(user, new UserDTO(), "LOOSE");
        // RoleDTO have to be mapped separately (probably due to short field list of RoleDTO)
        Mapper<Role, RoleDTO> mapper2 = new Mapper<>();
        userDTO.setRoleDTOList(mapper2.mapList(user.getRoles(), new RoleDTO(), "LOOSE"));
        return userDTO;
    }

}
