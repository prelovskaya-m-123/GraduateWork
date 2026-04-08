package ru.skypro.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.skypro.homework.dto.Role;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/webjars/**",
            "/login",
            "/register"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(authorization -> authorization
                        .requestMatchers(
                                "/swagger-resources/**",
                                "/swagger-ui.html",
                                "/v3/api-docs",
                                "/webjars/**",
                                "/login",
                                "/register"
                        ).permitAll()
                        .requestMatchers("/users/me", "/users/me/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                        .requestMatchers("/ads/me", "/ads/me/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                        .requestMatchers("/ads/**{id}/comments").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                        .requestMatchers("/ads", "/ads/**").authenticated()
                        .requestMatchers("/users", "/users/**").authenticated()
                        .anyRequest().authenticated()
                )
                .cors(withDefaults())
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
