package com.enigmacamp.challlcloanmanagement.config;

import com.enigmacamp.challlcloanmanagement.entity.User;
import com.enigmacamp.challlcloanmanagement.entity.enums.UserRole;
import com.enigmacamp.challlcloanmanagement.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AdminInitializer {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void initAdminUser() {
        if (!userRepository.existsByEmail("admin.root@mail.com")){
            User admin = User.builder()
                    .name("root")
                    .email("admin.root@mail.com")
                    .password(passwordEncoder.encode("root"))
                    .phoneNumber("08123456789")
                    .role(UserRole.valueOf("ADMIN"))
                    .build();

            userRepository.save(admin);
            System.out.println("Admin user created successfully with email : " + admin.getEmail() +" & " + "password : root");
        }
    }
}
