package com.example.fitnesstracker.service.workout;

import com.example.fitnesstracker.domain.workout.dto.CreateWorkoutDto;
import com.example.fitnesstracker.domain.workout.dto.ExerciseToExistingWorkoutDto;

public interface WorkoutService {
    void createWorkoutWithExercises(String username, CreateWorkoutDto createWorkoutDto);
    void addExerciseToExistingWorkout(String username, ExerciseToExistingWorkoutDto exerciseToExistingWorkoutDto);
}
