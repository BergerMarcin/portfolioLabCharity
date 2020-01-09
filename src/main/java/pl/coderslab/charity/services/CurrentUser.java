package pl.coderslab.charity.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import pl.coderslab.charity.dtos.CurrentUserDataDTO;

import java.util.Collection;

public class CurrentUser extends User {

    private final CurrentUserDataDTO currentUserDataDTO;

    public CurrentUser(String username,
                       String password,
                       Collection<? extends GrantedAuthority> authorities,
                       CurrentUserDataDTO currentUserDataDTO) {
        super(username, password, authorities);
        this.currentUserDataDTO = currentUserDataDTO;
    }

    public CurrentUserDataDTO getCurrentUserDataDTO() {
        return this.currentUserDataDTO;
    }
}
