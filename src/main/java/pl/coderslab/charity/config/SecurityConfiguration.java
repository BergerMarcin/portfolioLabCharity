package pl.coderslab.charity.config;
/*
TODO
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
*/

// TODO: required revision for/to the project


//TODO @Configuration
public class SecurityConfiguration {
/*
TODO
        extends WebSecurityConfigurerAdapter {

    // DataSource z konfiguracji application.properties. DataSource możemy sobie wstrzykiwać
    private final DataSource dataSource;

    public SecurityConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // ziarno dla hasłowania - metoda passwordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

// TODO: to check below identification on email (instead of previous identification on username)
        auth.jdbcAuthentication()
                .dataSource(dataSource)                  // z bazy danych MySQL (klasa z application.properties)
                .passwordEncoder(passwordEncoder())      // konfiguracja hasła -> wystawiamy Beana powyżej i mamy passwordEncoder
                .usersByUsernameQuery("SELECT first_name, last_name, password, role, active FROM users WHERE email = ?")  // pobierz imię, nazwisko, hasło, rolę, active poprzez email użytkownika
                .authoritiesByUsernameQuery("SELECT u.email, r.name FROM users u JOIN users_roles ur ON u.id = ur.user_id JOIN roles r ON ur.roles_id = r.id WHERE u.email = ?");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Security nie sprawdza żadnego elementu z katalogu css, images, js (gdzie trzymane są css, zdjęcia, JavaScript)
        web.ignoring()
                .antMatchers("/webapp/resources/css/")
                .antMatchers("/webapp/resources/images/")
                .antMatchers("/webapp/resources/js/")
                .antMatchers("/webjars/**");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // opcje ścieżek: .antMatchers("/user", "user/*", "user/**"); - tylko ścieżka user,
                //                                  tylko user i pierwsza podścieżka, user i wszystkie zagłębione podścieżki
                // .antMatchers("/").permitAll() - zezwala wszystkim
                // .antMatchers("/login").anonymous() - zezwala niezweryfikowanym
                // .antMatchers("/logout").authenticated() - zezwala zweryfikowanym
                // .antMatchers("/user", "/user/**").hasRole("USER") - zezwala zweryfikowanym/zalogowanym użytkownikom
                //                                                          hasRole dodaje "ROLE_" do "USER"
                // .antMatchers("/admin", "/admin/**").hasRole("ADMIN") - zezwala zweryfikowanym/zalogowanym administratorom,
                //                                                          hasRole dodaje "ROLE_" do "ADMIN"
                // .anyRequest().authenticated() - zezwala dla wszystkich nie zdefiniowanych powyżej ścieżek zalogowanych
                .antMatchers("/").permitAll()
                .antMatchers("/donation/*").permitAll()
                .antMatchers("/registration/*").permitAll()
                .antMatchers("/login").anonymous()
                .antMatchers("/logout").authenticated()
                .antMatchers("/user", "/user/**").hasRole("USER")
                .antMatchers("/admin", "/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                // Login page settings.
                .formLogin()
                .loginPage("/login")                // Security obsługuje działanie logowania na ścieżce "/login" (w tym wypadku
                                                    // LoginController.java + login.jsp; LoginController.java przekierowuje
                                                    // przez Get na login.jsp)
                .loginProcessingUrl("/login")       // wysłanie/zwrócenie wypełnionego formularza (z login.jsp)
                .usernameParameter("email")         // Security sam odbiera POST i sprawdza zgodność username (email) i password
                                                    //  z formularza - JUŻ NIE POTRZEBA obsługi post w LoginController.java
                .passwordParameter("password")      // j.w.
                .defaultSuccessUrl("/")             // jeżeli logowanie się powiodło Security przekierowuje na ścieżkę
                                                    // "/" (de facto index.jsp)
                .and()
                // Finishing after logout
                .logout()
                .logoutUrl("/logout")               // Security sam obsługuje działanie wylogowania przez POST na ścieżce
                                                    // "/logout" (w tym wypadku ten POST przychodzi bezpośrednio z viewera
                                                    // tj. z header.jsp.
                                                    // Dzięki temu nie potrzeba ani kontrolera ani viewera dla logout
                .logoutSuccessUrl("/")              // jeżeli logowanie się powiodło Security przekierowuje na ścieżkę
                                                    // "/" (de facto index.jsp)
                .and()
                .csrf();                            // generuje identyfikacyjny numer do kożdego pola formularza (w
        // formularzu *.jsp dodajemy <set:csrfInput/> w obrębie adnotacji <form>

        // Ponad powyższe globalne zabezpieczenia (typu ".antMatchers("/").permitAll()") można też indywidualnie/osobno
        // zabezpieczać metody dodając nad nimi adnotację @Security i parametryzując tą adnotację
    }
*/
}
