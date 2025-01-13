package com.footballteams.footballteamorganizer.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Define BCryptPasswordEncoder as a bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // retarded shit. daca nu merge un endpoint, 99% ca la .requestMatchers e problema
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                    .requestMatchers("/api/users/login",
                            "/api/users/signup",
                            "/api/games/upcoming",
                            "/api/games/join/**",
                            "/api/games/leave/**",
                            "/ws/**")

                .permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        return http.build();
    }

    // Define CORS configuration
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Specify the allowed origins (frontend URLs)
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));  // Adjust as needed
        // Specify the allowed HTTP methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Specify the allowed headers
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        // Allow credentials (e.g., cookies, authorization headers)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Apply the CORS configuration to all endpoints
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
