package com.footballteams.footballteamorganizer.Controllers;
import com.footballteams.footballteamorganizer.Models.Team;
import com.footballteams.footballteamorganizer.Services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    // Create a new team
    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        Team createdTeam = teamService.createTeam(team);
        return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
    }

    // Get a team by ID
    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Integer id) {
        Team team = teamService.getTeamById(id);
        if (team == null) {
            return ResponseEntity.notFound().build(); // Return 404 if team not found
        }
        return ResponseEntity.ok(team);
    }

    // Get all teams
    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = teamService.getAllTeams();
        return ResponseEntity.ok(teams);
    }

    // Update a team by ID
    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Integer id, @RequestBody Team teamDetails) {
        Team updatedTeam = teamService.updateTeam(id, teamDetails);
        if (updatedTeam == null) {
            return ResponseEntity.notFound().build(); // Return 404 if team not found
        }
        return ResponseEntity.ok(updatedTeam);
    }

    // Delete a team by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Integer id) {
        boolean isDeleted = teamService.deleteTeamById(id);
        if (!isDeleted) {
            return ResponseEntity.notFound().build(); // Return 404 if team not found
        }
        return ResponseEntity.noContent().build(); // Return 204 No Content when successfully deleted
    }
}

