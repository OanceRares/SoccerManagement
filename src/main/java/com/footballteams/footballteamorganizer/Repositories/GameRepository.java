package com.footballteams.footballteamorganizer.Repositories;

import com.footballteams.footballteamorganizer.Models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

}
