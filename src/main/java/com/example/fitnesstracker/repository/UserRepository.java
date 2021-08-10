package com.example.fitnesstracker.repository;

import com.example.fitnesstracker.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByVerificationCode(UUID code);
    Optional<User> findByForgotPasswordCode(UUID code);
    boolean existsByEmail(String email);
}
