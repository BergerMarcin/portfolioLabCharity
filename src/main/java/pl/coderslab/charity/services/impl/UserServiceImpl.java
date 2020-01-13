package pl.coderslab.charity.services.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.domain.entities.Role;
import pl.coderslab.charity.domain.entities.User;
import pl.coderslab.charity.domain.repositories.RoleRepository;
import pl.coderslab.charity.domain.repositories.UserRepository;
import pl.coderslab.charity.services.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Boolean isEmailUnique(String email) {
        return userRepository.findAllByEmail(email) == null;
    }

    @Override
    public User findAllByEmail(String email) {
        return userRepository.findAllByEmail(email);
    }

    @Override
    public User findAllWithUserInfoByEmail(String email) {
        return userRepository.findAllWithUserInfoByEmail(email);
    }
//    @Override
//    public User findAllWithRulesByEmailQuery(String email) {
//        return userRepository.findAllWithRulesByEmailQuery(email);
//    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(Boolean.TRUE);
        Role userRole = roleRepository.findAllByName("ROLE_USER");
        user.setRoles(new ArrayList<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }
}
