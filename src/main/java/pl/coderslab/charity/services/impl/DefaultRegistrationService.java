package pl.coderslab.charity.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.domain.entities.*;
import pl.coderslab.charity.domain.repositories.RoleRepository;
import pl.coderslab.charity.domain.repositories.UserRepository;
import pl.coderslab.charity.dtos.RegistrationDTO;
import pl.coderslab.charity.services.Mapper;
import pl.coderslab.charity.services.RegistrationService;
import pl.coderslab.charity.exceptions.EntityToDataBaseException;

@Service
@Transactional
@Slf4j
public class DefaultRegistrationService implements RegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public DefaultRegistrationService(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void register(RegistrationDTO registrationDTO) throws EntityToDataBaseException {
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! saveUser from Registration !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! ");
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! DefaultRegistrationService. registrationDTO to be mapped to user: {}", registrationDTO.toString());

        // mapping registrationDTO to user
        Mapper<RegistrationDTO, User> mapper = new Mapper<>();
        User user = mapper.mapObj(registrationDTO, new User(), "STANDARD");
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! DefaultRegistrationService. user (from registrationDTO) after simple mapping: {}", user.toString());

        // FILL-IN DEFAULT DATA/attributes (role, active) + ENCODE password
        // role is always USER (ADMIN might be set only via MySQL's console@localhost)
        Role roleUser = roleRepository.findAllByName("ROLE_USER");
        user.getRoles().add(roleUser);
        user.setActive(Boolean.TRUE);
        String encodedPassword = passwordEncoder.encode(registrationDTO.getPassword());
        user.setPassword(encodedPassword);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! DefaultRegistrationService. user (from registrationDTO) after add dafault parameters + encrypt password: {}", user.toString());

        // Final saving donation
        User userSaved = userRepository.save(user);
        log.debug("!!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! !!!!!!!!!!! DefaultRegistrationService. user saved: {}", user.toString());
        if (userSaved == null) {
            throw new EntityToDataBaseException("Wystąpił błąd przy zapisie danych. Powtórz całą operację");
        }
    }
}
