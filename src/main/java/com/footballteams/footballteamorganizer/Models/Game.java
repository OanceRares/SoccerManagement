package com.footballteams.footballteamorganizer.Models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;

    @ManyToMany
    @JoinTable(
            name = "game_black_team_players", // Join table for black team players
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private List<Player> blackTeam;

    @ManyToMany
    @JoinTable(
            name = "game_white_team_players", // Join table for white team players
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private List<Player> whiteTeam;

    private String result; // Result can be something like "3-2", or you could make this more complex if needed

    // Constructors
    public Game() {
    }

    public Game(LocalDate date, List<Player> blackTeam, List<Player> whiteTeam, String result) {
        this.date = date;
        this.blackTeam = blackTeam;
        this.whiteTeam = whiteTeam;
        this.result = result;
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Player> getBlackTeam() {
        return blackTeam;
    }

    public void setBlackTeam(List<Player> blackTeam) {
        this.blackTeam = blackTeam;
    }

    public List<Player> getWhiteTeam() {
        return whiteTeam;
    }

    public void setWhiteTeam(List<Player> whiteTeam) {
        this.whiteTeam = whiteTeam;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
