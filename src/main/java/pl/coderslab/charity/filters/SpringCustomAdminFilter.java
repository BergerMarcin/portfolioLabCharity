package pl.coderslab.charity.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import pl.coderslab.charity.services.CurrentUser;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter design acc.:
 * https://www.baeldung.com/spring-security-custom-filter
 * https://github.com/eugenp/tutorials/tree/master/spring-security-modules/spring-security-rest-basic-auth
 */

@WebFilter("/*")
@Slf4j
public class SpringCustomAdminFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!! SpringCustomAdminFilter. CHECK !!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!! SpringCustomAdminFilter. CHECK !!!!!!!!!!!!!!!!!!!!!!!!");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            CurrentUser currentUser = (CurrentUser)auth.getPrincipal();
            log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!! AdminFilter. currentUser: {}", currentUser.toString());
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!! SpringCustomAdminFilter. USER NOT AUTHORIZED!!!!");
            response.sendRedirect("/?msg=Wymagane+logowanie");
        }
    }

//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}

}
