package org.example.eewtihspringboot.component;

import lombok.RequiredArgsConstructor;
import org.example.eewtihspringboot.entity.User;
import org.example.eewtihspringboot.repo.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Override
    public void run(String... args) throws Exception {
        if (ddl.equals("create")) {
            userRepo.save(
                    User.builder()
                            .username("xasanov")
                            .password(passwordEncoder.encode("root123"))
                            .build()
            );
        }
    }
}
