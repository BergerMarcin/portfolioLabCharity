package pl.coderslab.charity.services;

import pl.coderslab.charity.domain.entities.User;
import pl.coderslab.charity.dtos.CurrentUserDataDTO;
import pl.coderslab.charity.dtos.RoleDataDTO;
import pl.coderslab.charity.dtos.UserDataDTO;
import pl.coderslab.charity.exceptions.EntityToDataBaseException;

import java.util.List;

public interface UserService {

    Boolean isEmailUnique (String email);

    UserDataDTO findById(Long id);

    List<UserDataDTO> findAllByRoleName(String roleName);


    void saveUser(UserDataDTO userDataDTO) throws EntityToDataBaseException;

    void saveAdmin(UserDataDTO userDataDTO, Boolean roleUser) throws EntityToDataBaseException;

    void updateAdmin(Long idProtected, UserDataDTO userDataDTO) throws EntityToDataBaseException;

}
