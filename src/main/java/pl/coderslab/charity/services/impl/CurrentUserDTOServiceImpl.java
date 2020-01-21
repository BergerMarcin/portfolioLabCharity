package pl.coderslab.charity.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.domain.entities.User;
import pl.coderslab.charity.domain.repositories.UserRepository;
import pl.coderslab.charity.dtos.CurrentUserDTO;
import pl.coderslab.charity.services.CurrentUserDTOService;
import pl.coderslab.charity.services.Mapper;

@Service
@Slf4j
public class CurrentUserDTOServiceImpl implements CurrentUserDTOService {

    private UserRepository userRepository;

    public CurrentUserDTOServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CurrentUserDTO readFromDB(String email) {
        User user = userRepository.findAllWithUserInfoByEmail(email);
        if (user == null) {return null;}
        // LOOSE method mapping nested objects of main/parent source object
        Mapper<User, CurrentUserDTO> mapper = new Mapper<>();
        return mapper.mapObj(user, new CurrentUserDTO(), "LOOSE");
    }

    @Override
    public String getPasswordFromDB(String email) {
        User user = userRepository.findAllByEmail(email);
        if (user == null) {return null;}
        return user.getPassword();
    }

}
