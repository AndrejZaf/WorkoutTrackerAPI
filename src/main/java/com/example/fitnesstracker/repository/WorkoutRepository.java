package com.example.fitnesstracker.repository;

import com.example.fitnesstracker.domain.workout.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    Optional<Workout> findByUid(UUID uid);
}
