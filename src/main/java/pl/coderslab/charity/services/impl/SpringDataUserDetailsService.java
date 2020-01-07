package pl.coderslab.charity.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.domain.entities.User;
import pl.coderslab.charity.services.CurrentUser;
import pl.coderslab.charity.services.UserService;

import java.util.HashSet;
import java.util.Set;

// Niniejszy serwis implementuje interfejs org.springframework.security.core.userdetails.UserDetailsService
// Serwis obsługuje/zwraca podstawowe dane o zalogowanym użytkowniku w obiekcie klasy-interface'u UserDetails należącej
//     do Spring Security (ta klasa posiada pola/atrybuty: username - w tym przypadku jest to email jako unikalna nazwa
//     użytkownika, hasło, authorities - role, inne Boolean dodawana automatycznie w klasie CurrentUser przy
//     rozszerzaniu klasy User implementującej UserDetails)

@Service
@Primary
@Slf4j
public class SpringDataUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

//    @Autowired
//    public void setUserRepository(UserService userService) {
//        this.userService = userService;
//    }

//!!!!!!!!!!!!! Ta pała wogóle tutaj nie włazi (nawet pobierając UserDetail a nie CurrentUser przez @AuthenticationPrincipal) !!!!!!!!!!!!!
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! {}", SecurityContextHolder.getContext().getAuthentication().getName());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! {}", email);
        User user = userService.findByEmail(email);
        // if user does not exists return exception informing username (in this case email)
        if (user == null) {throw new UsernameNotFoundException(email); }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        user.getRoles().forEach(r ->
                grantedAuthorities.add(new SimpleGrantedAuthority(r.getName())));
//        Original version
//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(), user.getPassword(), grantedAuthorities);
        // Version to return User details (details like first and last name)
        return new CurrentUser(user.getEmail(), user.getPassword(), grantedAuthorities, user);
    }
}
