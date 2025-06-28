package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // ✅ Public: Register new user
                        .requestMatchers(HttpMethod.POST, "/api/users", "/api/users/").permitAll()

                        // ✅ Public: View all users (optional)
                        .requestMatchers(HttpMethod.GET, "/api/users").permitAll()

                        // 🔒 Admin-restricted user operations
                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")

                        // 🔒 Admin-restricted destination, hotel, itinerary, expense
                        .requestMatchers("/api/destinations/**").hasRole("ADMIN")
                        .requestMatchers("/api/hotels/**").hasRole("ADMIN")
                        .requestMatchers("/api/itineraries/**").hasRole("ADMIN")
                        .requestMatchers("/api/expenses/**").hasRole("ADMIN")

                        // Feedback: anyone can GET, only logged in users can POST
                        .requestMatchers(HttpMethod.GET, "/api/feedback/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/feedback/**").authenticated()

                        // Trips: anyone can GET, only logged in users can POST
                        .requestMatchers(HttpMethod.GET, "/api/trips/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/trips/**").authenticated()

                        // Catch-all: must be authenticated
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    // For testing only – use BCryptPasswordEncoder in production!
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
