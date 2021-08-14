package com.example.fitnesstracker.repository;

import com.example.fitnesstracker.domain.workoutExercise.entity.WorkoutExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Long> {
}
