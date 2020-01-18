package pl.coderslab.charity.services;

import pl.coderslab.charity.domain.entities.User;
import pl.coderslab.charity.dtos.CurrentUserDataDTO;
import pl.coderslab.charity.dtos.RoleDataDTO;
import pl.coderslab.charity.dtos.UserDataDTO;
import pl.coderslab.charity.exceptions.EntityToDataBaseException;

import java.util.List;

public interface UserService {

    Boolean isEmailUnique (String email);

    UserDataDTO findAllById(Long id);

    List<UserDataDTO> findAllByRoleName(String roleName);

//    User findAllByEmail(String email);
//
//    User findAllWithUserInfoByEmail(String email);

    void saveUser(UserDataDTO userDataDTO) throws EntityToDataBaseException;

}
