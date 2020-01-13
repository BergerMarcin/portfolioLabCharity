package pl.coderslab.charity.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import pl.coderslab.charity.services.CurrentUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class SpringCustomSecurityHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication)
            throws IOException, ServletException {
        CurrentUser currentUser = (CurrentUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentUser.getAuthorities().toString().contains("ROLE_ADMIN")) {
            httpServletResponse.sendRedirect("/admin");
            return;
        }
        if (currentUser.getAuthorities().toString().contains("ROLE_USER")) {
            httpServletResponse.sendRedirect("/");
            return;
        }
        throw new IOException("Użytkownik nie ma przypisanej żadnej roli");
    }
}
