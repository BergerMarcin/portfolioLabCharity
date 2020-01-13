package pl.coderslab.charity.services;

import pl.coderslab.charity.domain.entities.User;
import pl.coderslab.charity.dtos.CurrentUserDataDTO;

public interface UserService {

    Boolean isEmailUnique (String email);

    User findAllByEmail(String email);

    User findAllWithUserInfoByEmail(String email);
//    User findAllWithRulesByEmailQuery(String email);

    void saveUser(User user);

}
