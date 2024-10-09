package com.footballteams.footballteamorganizer.Controllers;

import com.footballteams.footballteamorganizer.Models.Player;
import com.footballteams.footballteamorganizer.Services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Integer id) {
        Player player = playerService.getPlayerById(id);
        return ResponseEntity.ok(player);
    }

    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        Player savedPlayer = playerService.savePlayer(player);
        return new ResponseEntity<>(savedPlayer, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Player> deletePlayer(@PathVariable Integer id) {
        boolean isRemoved = playerService.deletePlayerById(id);
        if (!isRemoved) {
            return ResponseEntity.notFound().build(); // Return 404 if player not found
        }
        return ResponseEntity.noContent().build(); // Return 204 No Content on successful deletion
    }
}
