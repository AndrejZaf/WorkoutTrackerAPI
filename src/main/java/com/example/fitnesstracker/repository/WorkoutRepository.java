package com.example.fitnesstracker.repository;

import com.example.fitnesstracker.domain.workout.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
}
