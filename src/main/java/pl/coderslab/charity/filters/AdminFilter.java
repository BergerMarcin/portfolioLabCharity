package pl.coderslab.charity.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.coderslab.charity.services.CurrentUser;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Not working. Even not start-up (even log or sout basic infos does not print)
 */
@WebFilter("/*")
@Slf4j
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!! AdminFilter. CHECK !!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!! AdminFilter. CHECK !!!!!!!!!!!!!!!!!!!!!!!!");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            CurrentUser currentUser = (CurrentUser)auth.getPrincipal();
            log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!! AdminFilter. currentUser: {}", currentUser.toString());
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!! AdminFilter. USER NOT AUTHORIZED!!!!");
            response.sendRedirect("/?msg=Wymagane+logowanie");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
