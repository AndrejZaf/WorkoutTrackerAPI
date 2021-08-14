package com.example.fitnesstracker.repository;

import com.example.fitnesstracker.domain.exercise.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Optional<Exercise> findByUid(UUID uid);
}
