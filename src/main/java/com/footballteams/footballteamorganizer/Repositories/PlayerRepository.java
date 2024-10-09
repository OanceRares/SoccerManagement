package com.footballteams.footballteamorganizer.Repositories;

import com.footballteams.footballteamorganizer.Models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
}
