package com.bautista.controller;

import com.bautista.dto.DashboardStats;
import com.bautista.entity.UserData;
import com.bautista.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/stats")
    public ResponseEntity<?> getDashboardStats() {
        try {
            DashboardStats stats = adminService.getDashboardStats();
            return ResponseEntity.ok(stats);
        } catch (Exception ex) {
            log.error("Failed to get dashboard stats: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(ex.getMessage()));
        }
    }

    @GetMapping("/customers")
    public ResponseEntity<?> getAllCustomers() {
        try {
            List<UserData> customers = adminService.getAllCustomers();
            return ResponseEntity.ok(customers);
        } catch (Exception ex) {
            log.error("Failed to get customers: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(ex.getMessage()));
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<UserData> users = adminService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception ex) {
            log.error("Failed to get users: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(ex.getMessage()));
        }
    }

    @lombok.Data
    @lombok.AllArgsConstructor
    static class ErrorResponse {
        private String message;
    }
}