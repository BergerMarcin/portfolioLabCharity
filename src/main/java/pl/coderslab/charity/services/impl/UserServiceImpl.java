package pl.coderslab.charity.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.domain.entities.Role;
import pl.coderslab.charity.domain.entities.User;
import pl.coderslab.charity.domain.repositories.RoleRepository;
import pl.coderslab.charity.domain.repositories.UserRepository;
import pl.coderslab.charity.dtos.RoleDataDTO;
import pl.coderslab.charity.dtos.UserDataDTO;
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
    public List<UserDataDTO> findAllByRoleName (String roleName) {
        Role role = roleRepository.findAllByName(roleName);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllByRoleName role: {}", role.toString());
        List<User> userList = userRepository.findAllByRoles(role);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllByRoleName userList: {}", userList.toString());

        List<UserDataDTO> userDataDTOList = new ArrayList<>();
        for (User user: userList) {
            UserDataDTO userDataDTO = mapUserToUserDataDTO(user);
            userDataDTOList.add(userDataDTO);
            log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllByRoleName userDataDTO: {}", userDataDTO.toString());
            log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllByRoleName userDataDTO.getRoleDataDTOList: {}", userDataDTO.getRoleDataDTOList());
            log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllByRoleName userDataDTO.getUserInfoDTO: {}", userDataDTO.getUserInfoDataDTO());
        }
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllByRoleName userDataDTOList: {}", userDataDTOList.toString());
        return userDataDTOList;
    }

    @Override
    public UserDataDTO findById(Long id) {
        User user = userRepository.findAllById(id);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllById user: {}", user.toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllById user.getRoleDataDTOList: {}", user.getRoles().toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllById user.getUserInfoDTO: {}", user.getUserInfo());
        UserDataDTO userDataDTO = mapUserToUserDataDTO(user);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllById userDataDTO: {}", userDataDTO.toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllById userDataDTO.getRoleDataDTOList: {}", userDataDTO.getRoleDataDTOList().toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllById userDataDTO.getUserInfoDTO: {}", userDataDTO.getUserInfoDataDTO());
        return userDataDTO;
    }

    @Override
    public void saveNewUser(UserDataDTO userDataDTO) throws EntityToDataBaseException {
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! saveUser !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! UserServiceImpl.saveUser userDataDTO to be mapped to User: {}", userDataDTO.toString());

        User user = mapUserDataDTOToUser(userDataDTO);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! UserServiceImpl.saveUser user (from userDataDTO) after simple mapping: {}", user.toString());
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! UserServiceImpl.saveUser user.userInfo (from userDataDTO) after simple mapping: {}", user.getUserInfo().toString());
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! UserServiceImpl.saveUser user.roles (from userDataDTO) after simple mapping: {}", user.getRoles().toString());

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
    public void saveNewAdmin(UserDataDTO userDataDTO, Boolean roleUser) throws EntityToDataBaseException {
        // Set roleList (ROLE_ADMIN + ROLE_USER if roleUser) and then mapping it to roleDataDTOList.
        // Then roleDataDTOList is put into userDataDTO (with all new admin data) to be finally saved with saveUser method
        List<Role> roleList = new ArrayList<>();
        roleList.add(roleRepository.findAllByName("ROLE_ADMIN"));
        if (roleUser) {
            roleList.add(roleRepository.findAllByName("ROLE_USER"));
        }
        Mapper<Role, RoleDataDTO> mapper = new Mapper<>();
        List<RoleDataDTO> roleDataDTOList = mapper.mapList(roleList, new RoleDataDTO(),"STANDARD");
        if (roleDataDTOList == null) {
            throw new EntityToDataBaseException("Wystąpił błąd przy walidacji lub zapisie danych. Powtórz całą operację");
        }
        userDataDTO.setRoleDataDTOList(roleDataDTOList);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.addAdmin userDataDTO: {}", userDataDTO.toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.addAdmin userDataDTO.roleDataDTOList: {}", userDataDTO.getRoleDataDTOList().toString());

        // saving Admin (userDataDTO) as each User. Exception is coming from method saveUser
        saveNewUser(userDataDTO);
    }

    @Override
    public void updateAdmin(Long idProtected, UserDataDTO userDataDTO) throws EntityToDataBaseException {
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! updateAdmin !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! UserServiceImpl.updateAdmin userDataDTO to be mapped to User: {}", userDataDTO.toString());
        User oldUser = userRepository.findAllById(idProtected);

        User user =  mapUserDataDTOToUser(userDataDTO);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! UserServiceImpl.updateAdmin user (from userDataDTO) after simple mapping: {}", user.toString());

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
     * Separate method to map UserDataDTO to User
     * (due to issue mapping field with nested object RoleDataDTO to Role)
     * @param userDataDTO
     * @return
     */
    private User mapUserDataDTOToUser(UserDataDTO userDataDTO) {
        // Mapping UserDataDTO to User (issue with RolesDataDTO, so below mapping separately)
        Mapper<UserDataDTO, User> mapper1 = new Mapper();
        User user = mapper1.mapObj(userDataDTO, new User(),"STANDARD");
        // Mapping list of RoleDataDTO and set it to User
        Mapper<RoleDataDTO, Role> mapper2 = new Mapper();
        user.setRoles(mapper2.mapList(userDataDTO.getRoleDataDTOList(), new Role(),"STANDARD"));
        return user;
    }

    /**
     * Separate method to map User to UserDataDTO
     * (due to issue mapping field with nested object Role to RoleDataDTO)
     * @param user
     * @return
     */
    private UserDataDTO mapUserToUserDataDTO(User user) {
        // Mapping User to UserDataDTO (below maps UserInfoDTO but does not RoleDataDTO)
        Mapper<User, UserDataDTO> mapper1 = new Mapper<>();
        UserDataDTO userDataDTO = mapper1.mapObj(user, new UserDataDTO(), "LOOSE");
        // RoleDataDTO have to be mapped separately (probably due to short field list of RoleDataDTO)
        Mapper<Role, RoleDataDTO> mapper2 = new Mapper<>();
        userDataDTO.setRoleDataDTOList(mapper2.mapList(user.getRoles(), new RoleDataDTO(), "LOOSE"));
        return userDataDTO;
    }

}
