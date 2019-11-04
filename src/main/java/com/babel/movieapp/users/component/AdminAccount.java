package com.babel.movieapp.users.component;

import com.babel.movieapp.users.model.User;
import com.babel.movieapp.users.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AdminAccount implements ApplicationRunner {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public AdminAccount(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        UUID id = UUID.randomUUID();
        if (userRepository.existsById(id)) {
            return;
        }
        userRepository
                .save(new User(id, "admin", passwordEncoder.encode("admin"), "ADMIN"));
    }
}
