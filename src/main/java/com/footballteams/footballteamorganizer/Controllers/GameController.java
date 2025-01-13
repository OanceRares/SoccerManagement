package com.footballteams.footballteamorganizer.Controllers;

import com.footballteams.footballteamorganizer.JWT.JwtUtils;
import com.footballteams.footballteamorganizer.Models.Game;
import com.footballteams.footballteamorganizer.Services.GameService;
import com.footballteams.footballteamorganizer.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/games")
@CrossOrigin(origins = "http://localhost:3000")  // Adjust as needed
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private JwtUtils jwtUtils;

    // Create a new game (secured, e.g., only ADMIN)
    @PostMapping("/create")
    public ResponseEntity<?> createGame(@RequestBody Game game) {
        Game createdGame = gameService.createGame(game);
        return ResponseEntity.ok(createdGame);
    }

    // Get all upcoming games (secured)
    @GetMapping("/upcoming")
    public ResponseEntity<List<Game>> getUpcomingGames() {
        List<Game> games = gameService.getUpcomingGames();
        return ResponseEntity.ok(games);
    }

    @PostMapping("/join/{gameId}")
    public ResponseEntity<String> joinGame(
            @PathVariable Integer gameId,
            HttpServletRequest request) {
        try {
            // Extract the token from the Authorization header
            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header.");
            }

            String token = authHeader.substring(7); // Remove "Bearer " prefix
            String email = jwtUtils.getEmailFromToken(token); // Extract email from token

            // Add the user to the game using their email
            gameService.addUserToGame(gameId, email);

            return ResponseEntity.ok("User successfully joined the game.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


        @PostMapping("/leave/{gameId}")
        public ResponseEntity<String> leaveGame(
                @PathVariable Integer gameId,
                HttpServletRequest request) {
            try {
                String authHeader = request.getHeader("Authorization");

                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header.");
                }
                String token = authHeader.substring(7); // Remove "Bearer " prefix
                String email = jwtUtils.getEmailFromToken(token); // Extract email from token

                gameService.removeUserFromGame(gameId, email);

                return ResponseEntity.ok("User successfully left the game.");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }






    @GetMapping("/{id}")
    public ResponseEntity<?> getGameById(@PathVariable Integer id) {
        Game game = gameService.getGameById(id);
        if (game != null) {
            return ResponseEntity.ok(game);
        } else {
            return ResponseEntity.status(404).body("Game not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGame(@PathVariable Integer id, @RequestBody Game gameDetails) {
        Game updatedGame = gameService.updateGame(id, gameDetails);
        if (updatedGame != null) {
            return ResponseEntity.ok(updatedGame);
        } else {
            return ResponseEntity.status(404).body("Game not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGame(@PathVariable Integer id) {
        boolean deleted = gameService.deleteGameById(id);
        if (deleted) {
            return ResponseEntity.ok("Game deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Game not found");
        }
    }
}
