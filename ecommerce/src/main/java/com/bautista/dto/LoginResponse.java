package com.bautista.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private Integer id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private String token;  // For future JWT implementation
    private String message;
}