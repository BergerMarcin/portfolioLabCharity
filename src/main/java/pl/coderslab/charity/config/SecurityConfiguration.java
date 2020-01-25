package pl.coderslab.charity.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.coderslab.charity.services.impl.SpringDataUserDetailsService;

import javax.sql.DataSource;

/**
 * Authentication via email (as username) + password
 *
 */
//@EnableGlobalMethodSecurity(securedEnabled = true)    // Wymagana adnotacja w przypadku korzystania z adnotacji @Secured
                                                        // np. @Secured("ROLE_USER) na poziomie metod kontrolera czy
                                                        // serwisu (podobno można z niej zrezygnować, bo @EnableWebSecurity
                                                        // zostaje dodana przez Spring Boot)
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    // DataSource - dane z konfiguracji application.properties, niezbędne dla użycia bazy danych do autoryzacji
    // w mponiższej metodzie  configure(AuthenticationManagerBuilder auth). DataSource można wstrzykiwać
    private final DataSource dataSource;
    private final SpringCustomSecurityHandler springCustomSecurityHandler;

    public SecurityConfiguration(DataSource dataSource, SpringCustomSecurityHandler springCustomSecurityHandler) {
        this.dataSource = dataSource;
        this.springCustomSecurityHandler = springCustomSecurityHandler;
    }

    // ziarno dla hasłowania - metoda passwordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
//    dokładne określenie sposobu szyfrowania za pomocą BCryptPasswordEncoder
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    // ziarno podmiany customUserDetailsService na zwracanie naszego obiektu SpringDataUserDetailsService
    @Bean
    public SpringDataUserDetailsService customUserDetailsService() {
        return new SpringDataUserDetailsService();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // autoryzacja wg poniższych ustawień (niebezpieczne, bo łatwe do przechwycenia ze strony internetowej)
//        auth.inMemoryAuthentication()
//                .withUser("user1").password("{noop}user123").roles("USER")
//                .and()
//                .withUser("admin1").password("{noop}admin123").roles("ADMIN");

        // autoryzacja wg danych z bazy danych (tutaj uzupełniany jest obiekt UserDetail Spring Security,
        // więc wtedy nie działa metoda loadUserByUsername z klasy SpringDataUserDetailsService implementującej
        // UserDetailsService powołującej i wypełniającej UserDetail)
        // In case APPLYING usersByUsernameQuery & authoritiesByUsernameQuery THEY ARE OVERTAKEN UserDetail control (and filling)
//        auth.jdbcAuthentication()                        // autoryzacja wg zapisów bazy danych o sterowniku jdbc
//                .dataSource(dataSource)                  // autoryzacja na podstawie bazy danych podanej w application.properties (w tym wypadku  MySQL)
//                .passwordEncoder(passwordEncoder())      // konfiguracja hasła -> wystawiamy Beana powyżej i mamy passwordEncoder
//                .usersByUsernameQuery("SELECT email, password, active FROM users WHERE email = ?")    // email as username
//                .authoritiesByUsernameQuery("SELECT u.email, r.name FROM users u JOIN users_roles ur ON u.id = ur.user_id JOIN roles r ON ur.roles_id = r.id WHERE u.email = ?");

        // Z poniższej autoryzacji hasła należy skorzystać w przypadku użycia autoryzacji z bazy danych oraz użycia metoda
        // loadUserByUsername z klasy SpringDataUserDetailsService implementującej interface UserDetailsService dla
        // powołania i wypełnienia UserDetail będącej rozszerzeniem User Spring Security o dodatkowe dowolne pola
        auth.
                userDetailsService(customUserDetailsService()).
                passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Security nie sprawdza żadnego elementu z katalogu images, css, js (gdzie trzymane są zdjęcia, css, JavaScript).
        // Nie podano tutaj katalogów do css i JavaScript, bo te elementy sę wywoływane u użytkownika z URL (URL zabezpieczono poniżej)
        web.ignoring()
                .antMatchers("/resources/images/**")    // pictures
                .antMatchers("/resources/css/**")       // css
                .antMatchers("/resources/js/**")        // JavaScript
                .antMatchers("/webjars/**");            // residual from other project with webjar - BULMA
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // Access conditions to get to following URL
                // opcje ścieżek: .antMatchers("/user", "user/*", "user/**"); - tylko ścieżka user,
                //                                  tylko user i pierwsza podścieżka, user i wszystkie zagłębione
                //                                  podścieżki, po przecinku można łączyć ścieżki
                //                 .antMatchers(HttpMethod.POST, "/admin/user/add") - dana metoda
                // .antMatchers("/").permitAll() - zezwala wszystkim
                // .antMatchers("/nottestedfeature").denyAll()  - zabrania wszystkim
                // .antMatchers("/login").anonymous() - zezwala nieautoryzowanym
                // .antMatchers("/logout").authenticated() - zezwala autoryzowanym
                // .antMatchers("/user", "/user/**").hasRole("USER") - zezwala autoryzowanym o roli "USER"
                //                                                     (hasRole dodaje "ROLE_" do "USER")
                // .antMatchers("/admin", "/admin/**").hasRole("ADMIN") - zezwala autoryzowanym o roli "ADMIN"
                //                                                        (hasRole dodaje "ROLE_" do "ADMIN")
                // .antMatchers("/about/**").hasAnyRole("USER", "ADMIN") - zezwala autoryzowanym o roli "USER" lub "ADMIN"
                //                                                         (hasRole dodaje "ROLE_" do "USER" i "ADMIN")
                // .antMatchers("/admin", "/admin/**").hasIpAddress("79.184.211.92")    - zezwala pod danym adresem IP
                // .anyRequest().authenticated() - zezwala dla wszystkich nie zdefiniowanych powyżej ścieżek zalogowanych
                .antMatchers("/", "/home").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/index/**").permitAll()
                .antMatchers("/donation/*").permitAll()
                .antMatchers("/registration/*").permitAll()
                .antMatchers("/login").anonymous()
                .antMatchers("/logout").authenticated()
                .antMatchers("/user", "/user/**").hasAnyRole("USER", "SUPERADMIN")
                .antMatchers("/admin", "/admin/update").hasAnyRole("ADMIN", "SUPERADMIN")
                .antMatchers("/admin/add", "/admin/delete").hasRole("SUPERADMIN")
                .anyRequest().authenticated()
                .and()
                // Redirect to login
                .formLogin()                        // Przekierowanie do logowania w przypadku wejścia nieautoryzowanego
                                                    // na strony w/w. Logowanie wg poniższych ustawień:
                .loginPage("/login")                // Security obsługuje działanie logowania na ścieżce "/login" (w tym wypadku
                                                    // LoginController.java + login.jsp; LoginController.java przekierowuje
                                                    // przez Get na login.jsp)
                //.loginProcessingUrl("/login")       // wysłanie/zwrócenie wypełnionego formularza (z login.jsp)
                .usernameParameter("email")         // Security sam odbiera POST i sprawdza zgodność username (email) i password
                                                    //  z formularza - JUŻ NIE POTRZEBA obsługi post w LoginController.java
                .passwordParameter("password")      // j.w.
                //.defaultSuccessUrl("/")             // jeżeli logowanie się powiodło Security przekierowuje na ścieżkę
                                                    // "/" (de facto index.jsp)
                                                    // W PRZYPADKU istnienia successHandler, NIE STOSUJE SIĘ defaultSuccessUrl
                .successHandler(springCustomSecurityHandler)    // klasa SpringCustomSecurityHandler z metodą
                                                    // onAuthenticationSuccess, która wykonywana jest w przypadku autoryzacji
                .and()
                // Finishing after logout
                .logout()
                .logoutUrl("/logout")               // Security sam obsługuje działanie wylogowania przez POST na ścieżce
                                                    // "/logout" (w tym wypadku ten POST przychodzi bezpośrednio z viewera
                                                    // tj. z header.jsp.
                                                    // Dzięki temu nie potrzeba ani kontrolera ani viewera dla logout
                .logoutSuccessUrl("/login")         // jeżeli logowanie się powiodło Security przekierowuje na ścieżkę
                                                    // "/login"
                .and()
                .csrf()                            // generuje identyfikacyjny numer do kożdego pola formularza (w
                                                    // formularzu *.jsp dodajemy <set:csrfInput/> w obrębie adnotacji <form>
                .and()
                .exceptionHandling().accessDeniedPage("/403");  // przekierowania na widok 403.jsp przy niezgodnym z
                                                                // autoryzacją wejściem na dany zasób

        // Ponad powyższe globalne zabezpieczenia (typu ".antMatchers("/").permitAll()") można też indywidualnie/osobno
        // zabezpieczać metody dodając nad nimi adnotację @Security i parametryzując tą adnotację


        // Uruchamianie dodatkowego filtra po uruchomieniu wszystkich fitrów
//        http.addFilterAfter(new SpringCustomAdminFilter(), BasicAuthenticationFilter.class);
    }
}
