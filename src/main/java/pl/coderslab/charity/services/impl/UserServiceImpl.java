package pl.coderslab.charity.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.domain.entities.Role;
import pl.coderslab.charity.domain.entities.User;
import pl.coderslab.charity.domain.repositories.DonationRepository;
import pl.coderslab.charity.domain.repositories.RoleRepository;
import pl.coderslab.charity.domain.repositories.UserRepository;
import pl.coderslab.charity.dtos.RoleDTO;
import pl.coderslab.charity.dtos.UserDTO;
import pl.coderslab.charity.dtos.UserPassDTO;
import pl.coderslab.charity.exceptions.EntityToDataBaseException;
import pl.coderslab.charity.services.CurrentUser;
import pl.coderslab.charity.services.CurrentUserDTOService;
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
    private final CurrentUserDTOService currentUserDTOService;
    private final DonationRepository donationRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder, CurrentUserDTOService currentUserDTOService,
                           DonationRepository donationRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.currentUserDTOService = currentUserDTOService;
        this.donationRepository = donationRepository;
    }

    @Override
    public Boolean isEmailUnique(String email) {
        return userRepository.findAllByEmail(email) == null;
    }

    @Override
    public List<UserDTO> findAllByRoleNameAccAuthorisedRole(String roleName) {
        Role role = roleRepository.findAllByName(roleName);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllByRoleName role: {}", role.toString());

        // USERS/ADMINS and active/inactive are available acc. authorised role
        List<User> userList = new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            return null;
        }
        CurrentUser currentUser = (CurrentUser)auth.getPrincipal();
        if (currentUser.getAuthorities().toString().contains("SUPERADMIN")) {
            userList = userRepository.findAllByRoles(role);
        } else if (currentUser.getAuthorities().toString().contains("ADMIN")) {
            if (roleName.equals("ROLE_SUPERADMIN")) {
                return null;
            } else if (roleName.equals("ROLE_ADMIN")) {
                userList = userRepository.findAllByRolesAndActive(role, Boolean.TRUE);
            } else if (roleName.equals("ROLE_USER")) {
                userList = userRepository.findAllByRoles(role);
            }
        } else if (currentUser.getAuthorities().toString().contains("USER")) {
            if (roleName.equals("ROLE_SUPERADMIN")) {
                return null;
            } else if (roleName.equals("ROLE_ADMIN")) {
                return null;
            } else if (roleName.equals("ROLE_USER")) {
                userList = userRepository.findAllByRolesAndActive(role, Boolean.TRUE);
            }
        } else {
            return null;
        }

        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllByRoleName userList: {}", userList.toString());

        return mapObjUserToUserDTO(userList);
    }

    @Override
    public UserDTO findById(Long id) {
        User user = userRepository.findAllById(id);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllById user: {}", user.toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllById user.getRoleDTOList: {}", user.getRoles().toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllById user.getUserInfoDTO: {}", user.getUserInfo());
        UserDTO userDTO = mapObjUserToUserDTO(user);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllById userDTO: {}", userDTO.toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllById userDTO.getRoleDTOList: {}", userDTO.getRoleDTOList().toString());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.findAllById userDTO.getUserInfoDTO: {}", userDTO.getUserInfoDTO());
        return userDTO;
    }

    @Override
    public void saveNewUser(UserDTO userDTO) throws EntityToDataBaseException {
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! saveUser !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! UserServiceImpl.saveUser userDTO to be mapped to User: {}", userDTO.toString());

        User user = mapObjUserDTOToUser(userDTO);
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
    public void saveNewAdmin(UserDTO userDTO, Boolean ifRoleUser) throws EntityToDataBaseException {
        // Set roleList (ROLE_ADMIN + ROLE_USER if roleUser) and then mapping it to roleDTOList.
        // Then roleDTOList is put into userDTO (with all new admin data) to be finally saved with saveUser method
        List<Role> roleList = new ArrayList<>();
        roleList.add(roleRepository.findAllByName("ROLE_ADMIN"));
        if (ifRoleUser) {
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

        User user =  mapObjUserDTOToUser(userDTO);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! UserServiceImpl.updateAdmin user (from userDTO) after simple mapping: {}", user.toString());

        // Protection against unauthorised in fact update another record/line (instead of update the right one record/line)
        if (user.getId() == null || user.getId() != idProtected) {
            throw new EntityToDataBaseException("Wystąpił błąd przy walidacji lub zapisie danych. Powtórz całą operację");
        }

        // PASSWORD VALIDATION (checking password old with new-given-to-form)
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



    // TODO:    - change email and password - new methods & viewer
    //TODO: send an email informing about changes to old and new email.
    // email to both (both as unauthorised change might be done, so both emails should be informed): {oldUser.getEmail(), user.getEmail()}


    // userDTO has and passes password with field password
    @Override
    public void deleteAdmin(Long idProtected, UserDTO userDTO) throws EntityToDataBaseException {
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! UserServiceImpl.deleteUser START !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! UserServiceImpl.deleteUser idProtected: {}", idProtected);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! UserServiceImpl.deleteUser userPassDTO: {}", userDTO.toString());
        User user = userRepository.findAllById(idProtected);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! UserServiceImpl.deleteUser oldUser: {}", user.toString());

        // Protection against unauthorised change or some bug at getting data
        //    (which at the end will result in deleting wrong record/line)
        if (userDTO.getId() != idProtected) {
            throw new EntityToDataBaseException("Wystąpił błąd przy walidacji lub zapisie danych. Powtórz całą operację");
        }

        // ROLE_SUPERADMIN & SUPERADMIN PASSWORD VALIDATION (checking password of SUPERADMIN with given-to-form)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            throw new EntityToDataBaseException("Wymagana poprawna autoryzacja");
        }
        CurrentUser currentUser = (CurrentUser)auth.getPrincipal();
        if (!currentUser.getAuthorities().toString().contains("SUPERADMIN")) {
            throw new EntityToDataBaseException("Wymagana poprawna autoryzacja");
        }
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! UserServiceImpl.deleteUser userDTO.getPassword(): {}", userDTO.getPassword());
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! UserServiceImpl.deleteUser currentUser.getPassword(): {}", currentUser.getPassword());
        if (!passwordEncoder.matches(userDTO.getPassword(),
                currentUserDTOService.getPasswordFromDB(currentUser.getUsername()))) {
            throw new EntityToDataBaseException("Niepoprawne hasło. Powtórz całą operację");
        }

        // TODO: add user field to Donation entity & then uncomment below
        // Deleting all related donations
//        donationRepository.deleteAll(donationRepository.findAllWithCategoriesByUserOrderByUser(user));

        // Final deleting user
        userRepository.delete(user);

        //TODO: send an email informing about changes to old and new email.
        // email to both (both as unauthorised change might be done, so both emails should be informed): {oldUser.getEmail(), user.getEmail()}
    }



    /**
     * Separate method to map object UserDTO to object User
     * (due to issue mapping field with nested object RoleDTO to Role)
     * @param userDTO
     * @return
     */
    private User mapObjUserDTOToUser(UserDTO userDTO) {
        // Mapping UserDTO to User (issue with RoleDTO, so below mapping separately)
        Mapper<UserDTO, User> mapper1 = new Mapper();
        User user = mapper1.mapObj(userDTO, new User(),"STANDARD");
        // Mapping list of RoleDTO and set it to User
        Mapper<RoleDTO, Role> mapper2 = new Mapper();
        user.setRoles(mapper2.mapList(userDTO.getRoleDTOList(), new Role(),"STANDARD"));
        return user;
    }

    /**
     * Separate method to map object User to object UserDTO
     * (due to issue mapping field with nested object Role to RoleDTO)
     * @param user
     * @return
     */
    private UserDTO mapObjUserToUserDTO(User user) {
        // Mapping User to UserDTO (below maps UserInfoDTO but does not RoleDTO)
        Mapper<User, UserDTO> mapper1 = new Mapper<>();
        UserDTO userDTO = mapper1.mapObj(user, new UserDTO(), "LOOSE");
        // RoleDTO have to be mapped separately (probably due to short field list of RoleDTO)
        Mapper<Role, RoleDTO> mapper2 = new Mapper<>();
        userDTO.setRoleDTOList(mapper2.mapList(user.getRoles(), new RoleDTO(), "LOOSE"));
        return userDTO;
    }


    /**
     * Separate method to map list User to list UserDTO
     * (due to issue mapping field with nested object Role to RoleDTO)
     * @param userList
     * @return
     */
    private List<UserDTO> mapObjUserToUserDTO(List<User> userList) {
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : userList) {
            UserDTO userDTO = mapObjUserToUserDTO(user);
            userDTOList.add(userDTO);
            log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.mapObjUserToUserDTO userDTO: {}", userDTO.toString());
            log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.mapObjUserToUserDTO userDTO.getRoleDTOList: {}", userDTO.getRoleDTOList());
            log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! UserServiceImpl.mapObjUserToUserDTO userDTO.getUserInfoDTO: {}", userDTO.getUserInfoDTO());
        }
        return userDTOList;
    }

}
