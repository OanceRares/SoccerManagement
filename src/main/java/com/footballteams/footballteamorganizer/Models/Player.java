package com.footballteams.footballteamorganizer.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "Player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Assuming you're using auto-increment
    private Integer id;  // Changed from Long to Integer

    private String firstName;
    private String lastName;
    private Integer mmr;  // Player's MMR

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getMmr() {
        return mmr;
    }

    public void setMmr(Integer mmr) {
        this.mmr = mmr;
    }
}

