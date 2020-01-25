package pl.coderslab.charity.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.coderslab.charity.dtos.CurrentUserDTO;
import pl.coderslab.charity.services.CurrentUser;
import pl.coderslab.charity.services.CurrentUserDTOService;

import java.util.HashSet;
import java.util.Set;

// Niniejszy serwis implementuje interfejs org.springframework.security.core.userdetails.UserDetailsService
// Serwis obsługuje/zwraca podstawowe dane o logującym się użytkowniku w obiekcie klasy UserDetails należącej
//     do Spring Security a rozszerzającej User Spring Security (UserDetails posiada pola/atrybuty jak User Spring
//     Security: username - w tym przypadku jest to email jako unikalna nazwa użytkownika, hasło, authorities - role,
//     inne Boolean. Ponadto UserDetails może mieć też inne pola - w poniższym przypdaku wszystkie dane użytkownika
//     CurrentUserDTO)
// Sprawdzenie i zalogowanie następuje po wykonaniu metody loadUserByUsername wewnątrz Spring Security
@Slf4j
public class SpringDataUserDetailsService implements UserDetailsService {

    private CurrentUserDTOService currentUserDTOService;

    @Autowired
    private void setCurrentUserDTOService(CurrentUserDTOService currentUserDTOService) {
        this.currentUserDTOService = currentUserDTOService;
    }

    /**
     * Set UserDetails as CurrentUser (both extends User class of Spring Security)
     * Method takes user's basic data are teken from entity User (email=username, password, active, roles),
     * Method takes user's details data from entity UserInfo
     * Password checked and decision on authorisation is done internally by Spring Security based on method
     *    configure(AuthenticationManagerBuilder auth) of class SecurityConfiguration
     * @param email - username (unique String user parameter for that application)
     * @return
     * @throws UsernameNotFoundException
     */
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CurrentUserDTO currentUserDTO = currentUserDTOService.readFromDB(email);
        // if user does not exists, have not roles nor is not active return exception informing username (in this case email)
        if (currentUserDTO == null || currentUserDTO.getRoles() == null || !currentUserDTO.getActive()) {
            // also helps: session.invalidate() acc. https://stackoverflow.com/questions/44359792/log-out-user-by-admin-spring-security
            throw new UsernameNotFoundException(email);
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        currentUserDTO.getRoles().forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getName())));

        return new CurrentUser(currentUserDTO.getEmail(),
                currentUserDTOService.getPasswordFromDB(email),
                grantedAuthorities,
                currentUserDTO);
    }
}
