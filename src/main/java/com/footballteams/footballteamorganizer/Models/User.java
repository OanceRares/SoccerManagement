package com.footballteams.footballteamorganizer.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private Integer mmr;

    public User(String email, String password, String firstName, String lastName, String role, Integer mmr) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.mmr = mmr;
    }

    public User() {
    }

}
