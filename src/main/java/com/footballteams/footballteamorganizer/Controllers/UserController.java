package com.footballteams.footballteamorganizer.Controllers;

import com.footballteams.footballteamorganizer.JWT.TokenResponse;
import com.footballteams.footballteamorganizer.Models.User;
import com.footballteams.footballteamorganizer.Services.UserService;
import com.footballteams.footballteamorganizer.JWT.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")  // Update endpoint to 'users'
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getPlayerById(@PathVariable Integer id) {
        Optional<User> user = userService.getPlayerById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> foundUser = userService.authenticateUser(user.getEmail(), user.getPassword());
        if (foundUser.isPresent()) {
            String token = jwtUtils.generateTokenFromEmail(user.getEmail());  // You may change this logic to use email instead of username
            return ResponseEntity.ok(new TokenResponse(token));  // Return the JWT token
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
