package com.footballteams.footballteamorganizer.Repositories;

import com.footballteams.footballteamorganizer.Models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
    List<Game> findByGameDateTimeAfterOrderByGameDateTimeAsc(LocalDateTime currentDateTime);
}
