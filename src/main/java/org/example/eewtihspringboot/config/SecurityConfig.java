package org.example.eewtihspringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                manager -> {
                    manager
                            .requestMatchers(
                                    "/",
                                    "/bootstrap.min.css",
                                    "/basket",
                                    "/basket/add",
                                    "/basket/amount",
                                    "/basket/delete",
                                    "/products/image"
                            ).permitAll()
                            .requestMatchers("/post/add").authenticated()
                            .anyRequest().authenticated();
                }
        ).formLogin(formLogin -> {

        }).csrf().disable();
        return http.build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
