package com.footballteams.footballteamorganizer.Services;
import com.footballteams.footballteamorganizer.Models.Team;
import com.footballteams.footballteamorganizer.Repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    // Create a new team
    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    // Get a team by ID
    public Team getTeamById(Integer id) {
        Optional<Team> optionalTeam = teamRepository.findById(id);
        return optionalTeam.orElse(null); // Return null if the team is not found
    }

    // Get all teams
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    // Update an existing team
    public Team updateTeam(Integer id, Team teamDetails) {
        Optional<Team> optionalTeam = teamRepository.findById(id);

        if (optionalTeam.isPresent()) {
            Team team = optionalTeam.get();
            team.setName(teamDetails.getName());
            team.setPlayers(teamDetails.getPlayers());
            return teamRepository.save(team);
        } else {
            return null; // Handle case where team doesn't exist
        }
    }

    // Delete a team by ID
    public boolean deleteTeamById(Integer id) {
        if (teamRepository.existsById(id)) {
            teamRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
