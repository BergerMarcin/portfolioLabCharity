package pl.coderslab.charity.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import pl.coderslab.charity.dtos.CurrentUserDTO;

import java.util.Collection;

public class CurrentUser extends User {

    private final CurrentUserDTO currentUserDTO;

    public CurrentUser(String username,
                       String password,
                       Collection<? extends GrantedAuthority> authorities,
                       CurrentUserDTO currentUserDTO) {
        super(username, password, authorities);
        this.currentUserDTO = currentUserDTO;
    }

    public CurrentUserDTO getCurrentUserDTO() {
        return this.currentUserDTO;
    }
}
