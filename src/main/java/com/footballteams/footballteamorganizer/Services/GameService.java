package com.footballteams.footballteamorganizer.Services;

import com.footballteams.footballteamorganizer.Repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.footballteams.footballteamorganizer.Models.Game;

import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    public GameRepository gameRepository;

    // Create a new game
    public Game createGame(Game game) {
        return gameRepository.save(game);
    }

    // Get a game by ID
    public Game getGameById(Integer id) {
        Optional<Game> optionalGame = gameRepository.findById(id);
        return optionalGame.orElse(null); // Return the game if found, otherwise return null
    }

    // Get all games
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    // Update a game (for example, the result of the game)
    public Game updateGame(Integer id, Game gameDetails) {
        Optional<Game> optionalGame = gameRepository.findById(id);

        if (optionalGame.isPresent()) {
            Game game = optionalGame.get();
            game.setDate(gameDetails.getDate());
            game.setBlackTeam(gameDetails.getBlackTeam());
            game.setWhiteTeam(gameDetails.getWhiteTeam());
            game.setResult(gameDetails.getResult());
            return gameRepository.save(game);
        } else {
            return null; // Handle case where game doesn't exist
        }
    }

    // Delete a game by ID
    public boolean deleteGameById(Integer id) {
        if (gameRepository.existsById(id)) {
            gameRepository.deleteById(id);
            return true;
        }
        return false; // Return false if game doesn't exist
    }
}
