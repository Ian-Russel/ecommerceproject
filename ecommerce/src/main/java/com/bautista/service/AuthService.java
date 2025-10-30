package com.bautista.service;

import com.bautista.dto.LoginRequest;
import com.bautista.dto.LoginResponse;
import com.bautista.dto.SignupRequest;
import com.bautista.entity.UserData;
import com.bautista.repository.UserDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private UserDataRepository userRepository;

    public LoginResponse login(LoginRequest request) throws Exception {
        Optional<UserData> userOpt = userRepository.findByUsernameOrEmail(
                request.getUsername(),
                request.getUsername()
        );

        if (!userOpt.isPresent()) {
            throw new Exception("User not found");
        }

        UserData user = userOpt.get();

        if (!user.getPassword().equals(request.getPassword())) {
            throw new Exception("Invalid password");
        }

        if (!user.getIsActive()) {
            throw new Exception("Account is inactive");
        }

        user.setLastLogin(new Date());
        userRepository.save(user);

        LoginResponse response = new LoginResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setRole(user.getRole());
        response.setMessage("Login successful");

        return response;
    }

    public LoginResponse signup(SignupRequest request) throws Exception {
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            throw new Exception("Username is required");
        }

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new Exception("Email is required");
        }

        if (request.getPassword() == null || request.getPassword().length() < 6) {
            throw new Exception("Password must be at least 6 characters");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new Exception("Passwords do not match");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new Exception("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new Exception("Email already exists");
        }

        UserData newUser = new UserData();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(request.getPassword());
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setPhoneNumber(request.getPhoneNumber());
        newUser.setRole("CUSTOMER");
        newUser.setIsActive(true);
        newUser.setIsEmailVerified(false);

        UserData savedUser = userRepository.save(newUser);

        LoginResponse response = new LoginResponse();
        response.setId(savedUser.getId());
        response.setUsername(savedUser.getUsername());
        response.setEmail(savedUser.getEmail());
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setRole(savedUser.getRole());
        response.setMessage("Registration successful");

        return response;
    }

    public UserData getUserById(Integer id) throws Exception {
        return userRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found"));
    }

    public UserData updateUser(Integer id, UserData userData) throws Exception {
        UserData existingUser = getUserById(id);

        // Update allowed fields
        if (userData.getFirstName() != null) {
            existingUser.setFirstName(userData.getFirstName());
        }
        if (userData.getLastName() != null) {
            existingUser.setLastName(userData.getLastName());
        }
        if (userData.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(userData.getPhoneNumber());
        }
        if (userData.getAddress() != null) {
            existingUser.setAddress(userData.getAddress());
        }
        if (userData.getCity() != null) {
            existingUser.setCity(userData.getCity());
        }
        if (userData.getProvince() != null) {
            existingUser.setProvince(userData.getProvince());
        }
        if (userData.getPostalCode() != null) {
            existingUser.setPostalCode(userData.getPostalCode());
        }

        return userRepository.save(existingUser);
    }
}