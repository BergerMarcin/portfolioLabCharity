package pl.coderslab.charity.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.coderslab.charity.domain.entities.User;
import pl.coderslab.charity.services.CurrentUser;
import pl.coderslab.charity.services.UserService;

import java.util.HashSet;
import java.util.Set;

// Niniejszy serwis implementuje interfejs org.springframework.security.core.userdetails.UserDetailsService
// Serwis obsługuje/zwraca podstawowe dane o zalogowanym użytkowniku w obiekcie klasy UserDetails należącej do Spring
//     Security (ta klasa posiada pola/atrybuty: username - w tym przypadku jest to email jako unikalna nazwa
//     użytkownika, hasło, authorities - role)
public class SpringDataUserDetailsService {}
//implements UserDetailsService {
//
//    private UserService userService;
//
//    @Autowired
//    public void setUserRepository(UserService userService) {
//        this.userService = userService;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userService.findByEmail(email);
//        // if user does not exists return exception informing username (in this case email)
//        if (user == null) {throw new UsernameNotFoundException(email); }
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        user.getRoles().forEach(r ->
//                grantedAuthorities.add(new SimpleGrantedAuthority(r.getName())));
////        Original version
////        return new org.springframework.security.core.userdetails.User(
////                user.getEmail(), user.getPassword(), grantedAuthorities);
//        // Version to return User details (details like first and last name)
//        return new CurrentUser(user.getEmail(), user.getPassword(), grantedAuthorities, user);
//    }
//}
