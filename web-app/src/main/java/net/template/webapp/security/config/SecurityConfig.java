package net.template.webapp.security.config;

import jakarta.servlet.ServletException;
import net.template.server.security.util.SecurityUtil;
import net.template.server.security.util.config.CustomUserDetailsService;
import net.template.webapp.ApiConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/login", "/register", ApiConstants.APP_REST_CONTEXT_PATH + "/public/**").permitAll()
                        .requestMatchers(ApiConstants.APP_REST_CONTEXT_PATH + "/user/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(formLoginConfigurer -> formLoginConfigurer
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successForwardUrl("/user")
                        .permitAll()
                )
                .logout((httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.addLogoutHandler((request, response, authentication) -> {
                    try {
                        request.logout();
                    } catch (ServletException e) {
                        throw new RuntimeException(e);
                    }
                })))
                .httpBasic(httpBasicConfigurer -> httpBasicConfigurer.realmName("template"));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return SecurityUtil.encodePassword(rawPassword.toString());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                String encodedRawPassword = SecurityUtil.encodePassword(rawPassword.toString());
                return encodedRawPassword.equals(encodedPassword);
            }
        };
    }


    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }

}
