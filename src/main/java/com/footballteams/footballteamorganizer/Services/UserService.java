package com.footballteams.footballteamorganizer.Services;

import com.footballteams.footballteamorganizer.Models.User;
import com.footballteams.footballteamorganizer.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Autowiring PasswordEncoder

    // login
    public Optional<User> authenticateUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public User registerUser(User user) throws Exception {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("Email is already in use.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set default role if not set
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }
        return userRepository.save(user);
    }

    // Get user by email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> getPlayerById(Integer id) {
        return userRepository.findById(id);
    }
}
