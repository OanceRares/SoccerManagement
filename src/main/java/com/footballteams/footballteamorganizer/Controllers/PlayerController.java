package com.footballteams.footballteamorganizer.Controllers;

import com.footballteams.footballteamorganizer.Models.Player;
import com.footballteams.footballteamorganizer.Services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
