package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  // Disable CSRF for stateless API
                .authorizeRequests()
                .requestMatchers("/api/tickets/**").permitAll()  // Allow unauthenticated access to tickets API
                .anyRequest().authenticated()  // All other routes require authentication
                .and()
                .httpBasic();  // Enable basic authentication

        return http.build();
    }
}
