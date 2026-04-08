package ru.skypro.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
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
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorization -> authorization
                        .antMatchers(
                                "/swagger-resources/**",
                                "/swagger-ui.html",
                                "/v3/api-docs",
                                "/webjars/**",
                                "/login",
                                "/register",
                                "/images/**"
                        ).permitAll()

                        .antMatchers("/users/me", "/users/me/**")
                        .hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                        .antMatchers("/ads/me", "/ads/me/**")
                        .hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                        .antMatchers("/ads/**/comments")
                        .hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                        .antMatchers("/users/set_password")
                        .hasAnyRole(Role.USER.name(), Role.ADMIN.name())

                        .antMatchers(HttpMethod.GET, "/ads", "/ads/**")
                        .authenticated()

                        .antMatchers(HttpMethod.POST, "/ads")
                        .hasAnyRole(Role.USER.name(), Role.ADMIN.name())

                        .antMatchers(HttpMethod.DELETE, "/ads/{id}")
                        .authenticated()
                        .antMatchers(HttpMethod.PATCH, "/ads/{id}")
                        .authenticated()

                        .antMatchers(HttpMethod.DELETE, "/ads/{adId}/comments/{commentId}")
                        .authenticated()
                        .antMatchers(HttpMethod.PATCH, "/ads/{adId}/comments/{commentId}")
                        .authenticated()

                        .antMatchers(HttpMethod.PATCH, "/ads/{id}/image")
                        .hasAnyRole(Role.USER.name(), Role.ADMIN.name())

                        .antMatchers(HttpMethod.PATCH, "/users/me")
                        .hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                        .antMatchers(HttpMethod.PATCH, "/users/me/image")
                        .hasAnyRole(Role.USER.name(), Role.ADMIN.name())
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
