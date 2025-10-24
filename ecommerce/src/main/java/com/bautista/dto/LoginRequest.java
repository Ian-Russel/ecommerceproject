package com.bautista.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;  // Can be username or email
    private String password;
}