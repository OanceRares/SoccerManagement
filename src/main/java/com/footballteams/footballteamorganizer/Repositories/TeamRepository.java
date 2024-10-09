package com.footballteams.footballteamorganizer.Repositories;

import com.footballteams.footballteamorganizer.Models.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}