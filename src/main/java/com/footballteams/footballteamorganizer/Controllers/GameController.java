package com.footballteams.footballteamorganizer.Controllers;

import com.footballteams.footballteamorganizer.Models.Game;
import com.footballteams.footballteamorganizer.Services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    private GameService gameService;

    // Create a new game
    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody Game game) {
        Game createdGame = gameService.createGame(game);
        return new ResponseEntity<>(createdGame, HttpStatus.CREATED);
    }

    // Get a game by ID
    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable Integer id) {
        Game game = gameService.getGameById(id);
        if (game == null) {
            return ResponseEntity.notFound().build(); // 404 if the game is not found
        }
        return ResponseEntity.ok(game);
    }

    // Get all games
    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = gameService.getAllGames();
        return ResponseEntity.ok(games);
    }

    // Update a game by ID
    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable Integer id, @RequestBody Game gameDetails) {
        Game updatedGame = gameService.updateGame(id, gameDetails);
        if (updatedGame == null) {
            return ResponseEntity.notFound().build(); // 404 if the game does not exist
        }
        return ResponseEntity.ok(updatedGame);
    }

    // Delete a game by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Integer id) {
        boolean isDeleted = gameService.deleteGameById(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build(); // 404 if the game is not found
        }
        return ResponseEntity.noContent().build(); // 204 No Content when deletion is successful
    }
}
