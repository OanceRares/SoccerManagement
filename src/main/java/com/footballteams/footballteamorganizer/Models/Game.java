package com.footballteams.footballteamorganizer.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "game")  // Ensure the table name matches your database
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDate date;

    @Column(name = "game_datetime", nullable = false)
    private LocalDateTime gameDateTime;

    private String location;

    @Column(name = "max_participants")
    private Integer maxParticipants = 22;

    @Column(name = "current_participants")
    private Integer currentParticipants = 0;

    @ManyToMany
    @JoinTable(
            name = "game_black_team_players",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")  // Updated column name
    )
    private List<User> blackTeam;

    @ManyToMany
    @JoinTable(
            name = "game_white_team_players",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")  // Updated column name
    )
    private List<User> whiteTeam;

    private String result; // e.g., "3-2"

    // Constructors
    public Game() {
    }

    public Game(String title, String description, LocalDate date, LocalDateTime gameDateTime, String location, List<User> blackTeam, List<User> whiteTeam, String result) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.gameDateTime = gameDateTime;
        this.location = location;
        this.blackTeam = blackTeam;
        this.whiteTeam = whiteTeam;
        this.result = result;
        this.currentParticipants = 0;
    }

}
