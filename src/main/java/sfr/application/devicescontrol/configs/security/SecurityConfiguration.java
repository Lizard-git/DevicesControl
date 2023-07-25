package sfr.application.devicescontrol.configs.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import sfr.application.devicescontrol.services.SecurityService;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    private final PasswordEncoder passwordEncoder;

    private final SecurityService securityService;

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(securityService);
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(form -> form
                    .loginPage("/login")
                    .usernameParameter("login")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/home")
            )
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/login",
                            "/api/v1/**",
                            "/css/**",
                            "/js/**",
                            "/plugins/**",
                            "/image/**",
                            "/fonts/**").permitAll()
                    .anyRequest().authenticated()
            );
        return http.build();
    }
}
