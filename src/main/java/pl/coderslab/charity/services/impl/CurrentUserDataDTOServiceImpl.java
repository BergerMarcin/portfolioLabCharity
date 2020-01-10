package pl.coderslab.charity.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.domain.entities.User;
import pl.coderslab.charity.dtos.CurrentUserDataDTO;
import pl.coderslab.charity.services.CurrentUserDataDTOService;
import pl.coderslab.charity.services.UserService;

@Service
@Slf4j
public class CurrentUserDataDTOServiceImpl implements CurrentUserDataDTOService {

    private UserService userService;

    public CurrentUserDataDTOServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CurrentUserDataDTO readFromDB(String email) {
        User user = userService.findAllWithUserInfoByEmail(email);
        if (user == null) {return null;}
        ModelMapper modelMapper = new ModelMapper();
        // LOOSE method mapping nested objects of main/parent source object
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        CurrentUserDataDTO currentUserDataDTO = modelMapper.map(user, CurrentUserDataDTO.class);
        return currentUserDataDTO;
    }

    @Override
    public String getPasswordFromDB(String email) {
        User user = userService.findAllByEmail(email);
        if (user == null) {return null;}
        return user.getPassword();
    }
}
