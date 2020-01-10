package pl.coderslab.charity.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.coderslab.charity.domain.entities.Role;
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
    private void setCurrentUserDataDTOService(CurrentUserDataDTOService currentUserDataDTOService) {
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
        // if user does not exists, have not roles nor is not active return exception informing username (in this case email)
        if (currentUserDataDTO == null || currentUserDataDTO.getRoles() == null || !currentUserDataDTO.getActive()) {
            throw new UsernameNotFoundException(email);
        }

        // "Manual" choice of role. Especially important in case multiple roles (i.e. ADMIN) - based on that chosen view
        // If ROLE_USER not found should be reported to ADMIN
        for (Role role: currentUserDataDTO.getRoles()) {
            if (role.getName().equals("ROLE_USER")) {
                currentUserDataDTO.setChosenRole(role.getName());
            }
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        currentUserDataDTO.getRoles().forEach(role ->
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName())));

        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! SpringDataUserDetailsService. currentUserDataDTO.getChosenRole(): {}", currentUserDataDTO.getChosenRole());
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! SpringDataUserDetailsService. grantedAuthorities: {}", grantedAuthorities.toString());

        return new CurrentUser(currentUserDataDTO.getEmail(),
                currentUserDataDTOService.getPasswordFromDB(email),
                grantedAuthorities,
                currentUserDataDTO);
    }
}
