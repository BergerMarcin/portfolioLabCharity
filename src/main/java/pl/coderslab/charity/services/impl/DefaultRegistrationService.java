package pl.coderslab.charity.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
//TODO import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.domain.entities.*;
import pl.coderslab.charity.domain.repositories.RoleRepository;
import pl.coderslab.charity.domain.repositories.UserRepository;
import pl.coderslab.charity.dtos.RegistrationDataDTO;
import pl.coderslab.charity.services.RegistrationService;
import pl.coderslab.charity.services.SavingDataException;

@Service
@Transactional
@Slf4j
public class DefaultRegistrationService implements RegistrationService {

//TODO    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

//PasswordEncoder passwordEncoder,
    public DefaultRegistrationService(UserRepository userRepository, RoleRepository roleRepository) {
//TODO        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void register(RegistrationDataDTO registrationDataDTO) throws SavingDataException {
        // TODO:
        //  - check mapping if STANDARD matching strategy is OK
        //  - check ROLE set
        //  - password encoding

        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! saveUser from Registration !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! DefaultRegistrationService. registrationDataDTO to be mapped to user: {}", registrationDataDTO.toString());

        // mapping registrationDataDTO to user
        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User user = modelMapper.map(registrationDataDTO, User.class);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! DefaultRegistrationService. user (from registrationDataDTO) after simple mapping: {}", user.toString());

        // FILL-IN DEFAULT DATA/attributes (role, active) + ENCODE password
        // role is always USER (ADMIN might be set only via MySQL's console@localhost)
        Role roleUser = roleRepository.findAllByName("ROLE_USER");
        user.getRoles().add(roleUser);
        user.setActive(Boolean.TRUE);
// TODO        String encodedPassword = passwordEncoder.encode(registrationDataDTO.getPassword());
//        user.setPassword(encodedPassword);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! DefaultRegistrationService. user (from registrationDataDTO) after add dafault parameters + encrypt password: {}", user.toString());

        // Final saving donation
        User userSaved = userRepository.save(user);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! DefaultRegistrationService. user saved: {}", user.toString());
        if (userSaved == null) {
            throw new SavingDataException("Wystąpił błąd przy zapisie danych. Powtórz całą operację");
        }
    }
}
