package com.footballteams.footballteamorganizer.JWT;

public class TokenResponse {

    private String token;

    // Constructor
    public TokenResponse(String token) {
        this.token = token;
    }

    // Getter
    public String getToken() {
        return token;
    }

    // Setter
    public void setToken(String token) {
        this.token = token;
    }
}
