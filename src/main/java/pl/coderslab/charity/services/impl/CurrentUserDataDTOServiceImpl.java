package pl.coderslab.charity.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.domain.entities.User;
import pl.coderslab.charity.domain.repositories.UserRepository;
import pl.coderslab.charity.dtos.CurrentUserDataDTO;
import pl.coderslab.charity.services.CurrentUserDataDTOService;

@Service
@Slf4j
public class CurrentUserDataDTOServiceImpl implements CurrentUserDataDTOService {

    private UserRepository userRepository;

    public CurrentUserDataDTOServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CurrentUserDataDTO readFromDB(String email) {
        User user = userRepository.findAllWithUserInfoByEmail(email);
        if (user == null) {return null;}
        ModelMapper modelMapper = new ModelMapper();
        // LOOSE method mapping nested objects of main/parent source object
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        CurrentUserDataDTO currentUserDataDTO = modelMapper.map(user, CurrentUserDataDTO.class);
        return currentUserDataDTO;
    }

    @Override
    public String getPasswordFromDB(String email) {
        User user = userRepository.findAllByEmail(email);
        if (user == null) {return null;}
        return user.getPassword();
    }
}
