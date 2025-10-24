package com.bautista.controller;

import com.bautista.dto.LoginRequest;
import com.bautista.dto.LoginResponse;
import com.bautista.dto.SignupRequest;
import com.bautista.entity.UserData;
import com.bautista.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            log.info("Login attempt for user: {}", request.getUsername());
            LoginResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Login failed: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(ex.getMessage()));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        try {
            log.info("Signup attempt for username: {}", request.getUsername());
            LoginResponse response = authService.signup(request);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            log.error("Signup failed: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(ex.getMessage()));
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable Integer id) {
        try {
            UserData user = authService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (Exception ex) {
            log.error("Failed to get user: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(ex.getMessage()));
        }
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody UserData userData) {
        try {
            UserData updated = authService.updateUser(id, userData);
            return ResponseEntity.ok(updated);
        } catch (Exception ex) {
            log.error("Failed to update user: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(ex.getMessage()));
        }
    }

    // Error response class
    @lombok.Data
    @lombok.AllArgsConstructor
    static class ErrorResponse {
        private String message;
    }
}