package com.footballteams.footballteamorganizer.Services;

import com.footballteams.footballteamorganizer.Repositories.GameRepository;
import com.footballteams.footballteamorganizer.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.footballteams.footballteamorganizer.Models.Game;
import com.footballteams.footballteamorganizer.Models.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    public GameRepository gameRepository;

    @Autowired
    public UserRepository userRepository;

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

    // Get all upcoming games
    public List<Game> getUpcomingGames() {
        return gameRepository.findByGameDateTimeAfterOrderByGameDateTimeAsc(LocalDateTime.now());
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
            game.setGameDateTime(gameDetails.getGameDateTime());
            game.setLocation(gameDetails.getLocation());
            game.setMaxParticipants(gameDetails.getMaxParticipants());
            game.setCurrentParticipants(gameDetails.getCurrentParticipants());
            game.setTitle(gameDetails.getTitle());
            game.setDescription(gameDetails.getDescription());
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

    public boolean joinGame(Integer gameId, Integer userId) {
        Optional<Game> optionalGame = gameRepository.findById(gameId);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalGame.isPresent() && optionalUser.isPresent()) {
            Game game = optionalGame.get();
            User user = optionalUser.get();

            if (game.getCurrentParticipants() < game.getMaxParticipants()) {
                if (!game.getBlackTeam().contains(user) && !game.getWhiteTeam().contains(user)) {
                    game.getBlackTeam().add(user);
                    game.setCurrentParticipants(game.getCurrentParticipants() + 1);
                    gameRepository.save(game);
                    return true;
                }
            }
        }
        return false;
    }

    public void addUserToGame(Integer gameId, String email) {
        // Fetch the game
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));

        // Fetch the user
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the user is already part of a team
        if (game.getBlackTeam().contains(user) || game.getWhiteTeam().contains(user)) {
            throw new RuntimeException("User is already assigned to a team in this game");
        }

        // Assign the user to the team with fewer players
        if (game.getBlackTeam().size() <= game.getWhiteTeam().size()) {
            game.getBlackTeam().add(user);
        } else {
            game.getWhiteTeam().add(user);
        }

        // Save the updated game
        gameRepository.save(game);
    }

    public void removeUserFromGame(Integer gameId, String email) throws Exception {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new Exception("Game not found"));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found"));

        if (game.getBlackTeam().contains(user)) {
            game.getBlackTeam().remove(user);
        } else if (game.getWhiteTeam().contains(user)) {
            game.getWhiteTeam().remove(user);
        } else {
            throw new Exception("User is not part of this game.");
        }

        gameRepository.save(game); // Save the updated game
    }
}
