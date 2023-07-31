package com.example.SpringBoot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/bootstrap/**").permitAll()
                        .requestMatchers(toH2Console()).permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/logout").permitAll()
                        .requestMatchers("/registration/**").permitAll()
                        .requestMatchers("/shelters").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/shelter-form").hasAnyRole("ADMIN")
                        .requestMatchers("/shelters/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/delete-shelter/**").hasAnyRole("ADMIN")
                        .requestMatchers("/edit-shelter/**").hasAnyRole("ADMIN")
                        .requestMatchers("/animals").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/animals/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/animal-form").hasAnyRole("ADMIN")
                        .requestMatchers("/delete-animal/**").hasAnyRole("ADMIN")
                        .requestMatchers("/edit-animal/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions().disable())
                .csrf(csrf -> csrf.ignoringRequestMatchers(toH2Console()))
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/animals")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll())
                .exceptionHandling().accessDeniedPage("/access-denied");
        return http.build();
    }
}