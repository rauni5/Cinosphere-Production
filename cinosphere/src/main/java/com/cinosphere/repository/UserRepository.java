// src/main/java/com/cinosphere/repository/UserRepository.java
package com.cinosphere.repository;

import com.cinosphere.model.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UsersModel, Integer> {
    Optional<UsersModel> findByUsername(String username);
    Optional<UsersModel> findByEmail(String email);
    List<UsersModel> findByIsActive(boolean isActive);
    List<UsersModel> findByUsernameContaining(String username);
    long countByRegistrationDate(LocalDate date);
}