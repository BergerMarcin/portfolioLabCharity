package pl.coderslab.charity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.coderslab.charity.converters.RoleDTOConverter;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    // Declaring converter for Role
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(roleDTOConverter());
    }
    @Bean
    public RoleDTOConverter roleDTOConverter() {
        return new RoleDTOConverter();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        // registry.addViewController("/login").setViewName("login");
        // Fakultatywne=nadmiarowe przekieranie ścieżki "/login" na "login.jsp" (zastępuje w całości LoginController.java,
        // bo jest tam tylko 1 metoda przekierowująca przez GET na formularz; natomiast POST z formularza jest obsługiwana
        // przez Security zgodnie z SecurityConfiguration.java metodą void configure(HttpSecurity http) i
        // http.authorizeRequests().....formLogin())

        // registry.addViewController("/403").setViewName("403");
        // Ścieżka "/403" to brak dostępu do określonego zasobu. Na 403.jsp wyświetlimy w przejrzysty sposób informację o braku dostępu do zasobu

//        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/403").setViewName("403");

    }
}
