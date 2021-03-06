package pl.coderslab.charity.services;

import pl.coderslab.charity.dtos.UserDTO;
import pl.coderslab.charity.dtos.UserPassDTO;
import pl.coderslab.charity.exceptions.EntityToDataBaseException;

import java.util.List;

public interface UserService {

    Boolean isEmailUnique (String email);

    UserDTO findById(Long id);

    List<UserDTO> findAllByRoleNameAccAuthorisedRole(String roleName);



    void saveNewUser(UserDTO userDTO) throws EntityToDataBaseException;

    void saveNewAdmin(UserDTO userDTO, Boolean ifRoleUser) throws EntityToDataBaseException;

    void updateAdmin(Long idProtected, UserDTO userDTO) throws EntityToDataBaseException;

//    void updateAdminEmailAndPassword ();

    void deleteAdmin(Long idProtected, UserDTO userDTO) throws EntityToDataBaseException;

}
