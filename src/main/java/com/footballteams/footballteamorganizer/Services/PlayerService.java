package com.footballteams.footballteamorganizer.Services;

import com.footballteams.footballteamorganizer.Models.Player;
import com.footballteams.footballteamorganizer.Repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    public Player getPlayerById(Integer id){
        return playerRepository.findById(id).orElseThrow(() -> new RuntimeException("Player not found"));
    }

}
