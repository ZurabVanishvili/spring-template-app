package net.template.webapp.security.config;

import net.template.server.security.util.SecurityUtil;
import net.template.server.security.config.CustomUserDetailsService;
import net.template.webapp.ApiConstants;
import net.template.webapp.security.filter.LoginFilter;
import net.template.webapp.security.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Value("${app.security.authorization-type}")
    private String authType;

    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        switch (authType.toUpperCase()) {
            case "FORM": {
                configureFormAuthorization(http);
                break;
            }
            case "JWT": {
                configureJwtAuthorization(http);
                break;
            }
        }

        return http.build();

    }

    private void configureJwtAuthorization(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(
                                "/public/**",
                                "/login/**",
                                "/error",
                                ApiConstants.APP_REST_CONTEXT_PATH + "/jwt/**",
                                ApiConstants.APP_REST_CONTEXT_PATH + "/anonymous/**")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    private void configureFormAuthorization(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(
                                "/public/**",
                                "/login/**",
                                ApiConstants.APP_REST_CONTEXT_PATH + "/anonymous/**")
                        .permitAll()
                        .requestMatchers(ApiConstants.APP_REST_CONTEXT_PATH + "/user/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(formLoginConfigurer -> formLoginConfigurer
                        .loginPage("/public/login")
                        .loginProcessingUrl("/public/login")
                        .defaultSuccessUrl("/app")
                        .successHandler((request, response, authentication) -> response.sendRedirect("/app" + ApiConstants.APP_REST_CONTEXT_PATH + "/user"))
                        .permitAll()
                )
                .logout((httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutSuccessUrl("/login")))
                .addFilterBefore(new LoginFilter("/app"), DefaultLoginPageGeneratingFilter.class);

    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
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

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean(name = "authType")
    public String authType() {
        return authType;
    }
}
