
package com.Moses.Moses.POS.System.configuration;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http

                // Enable CORS
                .cors(cors ->
                        cors.configurationSource(corsConfigurationSource())
                )

                // Disable CSRF for JWT-based authentication
                .csrf(csrf -> csrf.disable())

                // Stateless authentication
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Endpoint authorization
                .authorizeHttpRequests(auth -> auth

                        // Allow browser preflight requests
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Public endpoints
                        .requestMatchers(
                                "/auth/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Super Admin endpoints
                        .requestMatchers("/api/super-admin/**")
                        .hasRole("ADMIN")

                        // All other API endpoints require authentication
                        .requestMatchers("/api/**")
                        .authenticated()

                        // Other non-API endpoints
                        .anyRequest()
                        .permitAll()
                )

                // JWT validation filter
                .addFilterBefore(
                        new JwtValidator(),
                        BasicAuthenticationFilter.class
                )

                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        return new CorsConfigurationSource() {

            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                CorsConfiguration config = new CorsConfiguration();

                // React Vite frontend
                config.setAllowedOrigins(List.of(
                        "http://localhost:5173",
                        "http://localhost:3000"
                ));

                // Allowed HTTP methods
                config.setAllowedMethods(List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "PATCH",
                        "DELETE",
                        "OPTIONS"
                ));

                // Allow Authorization and Content-Type headers
                config.setAllowedHeaders(List.of("*"));

                // Allow credentials
                config.setAllowCredentials(true);

                // Cache preflight response
                config.setMaxAge(3600L);

                return config;
            }
        };
    }
}
