package com.example.studentmgmt.config;

import com.example.studentmgmt.entity.User;
import com.example.studentmgmt.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.example.studentmgmt.enums.Role.ROLE_ADMIN;

@Configuration
public class AdminInitializer {

    @Bean
    public CommandLineRunner createAdmin(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            String adminUsername = "admin";

            if (!repo.existsByUsername(adminUsername)) {
                User u = new User();
                u.setUsername(adminUsername);
                u.setPassword(encoder.encode("admin"));
                u.setRole(ROLE_ADMIN);
                u.setActive(true);
                repo.save(u);
                System.out.println("Admin user created: admin / admin");
            }
        };
    }
}
