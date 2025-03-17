package com.company.dto;

public class JwtResponse {
    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }

    // Getter / Setter
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
