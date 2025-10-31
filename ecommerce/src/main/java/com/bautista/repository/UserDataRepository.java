package com.bautista.repository;

import com.bautista.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserDataRepository extends JpaRepository<UserData, Integer> {

    Optional<UserData> findByUsername(String username);

    Optional<UserData> findByEmail(String email);

    Optional<UserData> findByUsernameOrEmail(String username, String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    List<UserData> findByRole(String role);

    Long countByRole(String role);

    Long countByRoleAndCreatedAtAfter(String role, Date date);
}