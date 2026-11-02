package com.badri.employee_management_system.Config;

import com.badri.employee_management_system.Entity.Role;
import com.badri.employee_management_system.Entity.User;
import com.badri.employee_management_system.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
    @Value("${APP_ADMIN_USERNAME:}")
    private String adminUsername;

    @Value("${APP_ADMIN_EMAIL:}")
    private String adminEmail;

    @Value("${APP_ADMIN_PASSWORD:}")
    private String adminPassword;

    @Bean
    CommandLineRunner createAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (adminUsername.isBlank()
                    || adminEmail.isBlank()
                    || adminPassword.isBlank()) {

                System.out.println("Admin initialization skipped: credentials not configured.");
                return;
            }

            if (!userRepository.existsByUsername(adminUsername)) {

                User admin = new User();

                admin.setUsername(adminUsername);
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setRole(Role.ADMIN);

                userRepository.save(admin);

                System.out.println("Admin user created successfully.");
            }
        };
    }
}
