package com.example.fitnesstracker.service.workout;

import com.example.fitnesstracker.domain.workout.WorkoutResponse;
import com.example.fitnesstracker.domain.workout.dto.CreateWorkoutDto;
import com.example.fitnesstracker.domain.workout.dto.ExerciseToExistingWorkoutDto;
import com.example.fitnesstracker.domain.workout.entity.Workout;

import java.util.List;
import java.util.Set;

public interface WorkoutService {
    List<WorkoutResponse> getWorkoutsForUser(String email);
    String createWorkoutWithExercises(String username, CreateWorkoutDto createWorkoutDto);
    String addExerciseToExistingWorkout(String username, ExerciseToExistingWorkoutDto exerciseToExistingWorkoutDto);
    WorkoutResponse getWorkoutByUid(String uid);
    void deleteWorkoutByUid(String uid);
}
