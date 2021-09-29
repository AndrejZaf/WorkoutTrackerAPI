package com.example.fitnesstracker.service.workout;

import com.example.fitnesstracker.domain.workout.WorkoutResponse;
import com.example.fitnesstracker.domain.workout.dto.CreateWorkoutDto;
import com.example.fitnesstracker.domain.workout.dto.ExerciseToExistingWorkoutDto;

import java.util.List;

public interface WorkoutService {
    List<WorkoutResponse> getWorkoutsForUser(String email);
    String createWorkoutWithExercises(String username, CreateWorkoutDto createWorkoutDto);
    String addExerciseToExistingWorkout(String username, ExerciseToExistingWorkoutDto exerciseToExistingWorkoutDto);
    WorkoutResponse getWorkoutByUid(String uid);
    void removeExerciseFromWorkout(String workoutUid, String exerciseUid);
    void deleteWorkoutByUid(String uid);
}
