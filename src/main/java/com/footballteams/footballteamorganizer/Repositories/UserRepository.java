package com.footballteams.footballteamorganizer.Repositories;

import com.footballteams.footballteamorganizer.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);  // Find user by email
}
