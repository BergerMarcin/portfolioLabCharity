package pl.coderslab.charity.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.coderslab.charity.dtos.CurrentUserDataDTO;
import pl.coderslab.charity.services.CurrentUser;
import pl.coderslab.charity.services.CurrentUserDataDTOService;

import java.util.HashSet;
import java.util.Set;

// Niniejszy serwis implementuje interfejs org.springframework.security.core.userdetails.UserDetailsService
// Serwis obsługuje/zwraca podstawowe dane o logującym się użytkowniku w obiekcie klasy UserDetails należącej
//     do Spring Security a rozszerzającej User Spring Security (UserDetails posiada pola/atrybuty jak User Spring
//     Security: username - w tym przypadku jest to email jako unikalna nazwa użytkownika, hasło, authorities - role,
//     inne Boolean. Ponadto UserDetails może mieć też inne pola - w poniższym przypdaku wszystkie dane użytkownika
//     CurrentUserDataDTO)
// Sprawdzenie i zalogowanie następuje po wykonaniu metody loadUserByUsername wewnątrz Spring Security
@Slf4j
public class SpringDataUserDetailsService implements UserDetailsService {

    private CurrentUserDataDTOService currentUserDataDTOService;

    @Autowired
    public void setCurrentUserDataDTOService(CurrentUserDataDTOService currentUserDataDTOService) {
        this.currentUserDataDTOService = currentUserDataDTOService;
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
        CurrentUserDataDTO currentUserDataDTO = currentUserDataDTOService.readFromDB(email);
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! City: {}", currentUserDataDTO.getCity());
        // if user does not exists, inactive nor wrong password return exception informing username (in this case email)
        if (currentUserDataDTO == null || !currentUserDataDTO.getActive()) {
            throw new UsernameNotFoundException(email);
        } else {
//TODO: check if email is OK. If not throw exception

//            if () {
//                throw new UsernameNotFoundException(email);
//            }
        }
        // if wrong password return exception informing username (in this case email)

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        currentUserDataDTO.getRoles().forEach(r ->
                grantedAuthorities.add(new SimpleGrantedAuthority(r.getName())));
        return new CurrentUser(currentUserDataDTO.getEmail(),
                currentUserDataDTOService.getPasswordFromDB(email),
                grantedAuthorities,
                currentUserDataDTO);
    }
}
