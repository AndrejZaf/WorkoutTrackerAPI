package com.example.fitnesstracker.service.workout;

import com.example.fitnesstracker.domain.workout.WorkoutDTO;
import com.example.fitnesstracker.domain.workout.dto.CreateWorkoutDTO;
import com.example.fitnesstracker.domain.workout.dto.ExerciseToExistingWorkoutDTO;
import com.example.fitnesstracker.domain.workout.entity.Workout;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface WorkoutService {
    List<Workout> getWorkoutsForUser(String email);
    String createWorkoutWithExercises(String username, CreateWorkoutDTO createWorkoutDto);
    String addExerciseToExistingWorkout(String username, ExerciseToExistingWorkoutDTO exerciseToExistingWorkoutDto);
    Workout getWorkoutByUid(String uid);
    void removeExerciseFromWorkout(String workoutUid, String exerciseUid);
    void deleteWorkoutByUid(String uid);
    void updateWorkout(String workoutUid, CreateWorkoutDTO createWorkoutDto);
}
