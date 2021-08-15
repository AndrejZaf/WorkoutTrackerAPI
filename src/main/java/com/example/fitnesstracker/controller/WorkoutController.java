package com.example.fitnesstracker.controller;

import com.example.fitnesstracker.domain.workout.dto.CreateWorkoutDto;
import com.example.fitnesstracker.domain.workout.dto.ExerciseToExistingWorkoutDto;
import com.example.fitnesstracker.service.workout.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("${spring.data.rest.base-path}/workout")
@RequiredArgsConstructor
public class WorkoutController {
    private final WorkoutService workoutService;

    @GetMapping
    ResponseEntity<?> getWorkoutsPerUser(Principal principal){
        // TODO (1): Implement the workouts per user and prepare for clean up so far because the code is getting big
        // TODO (2): REMEMBER ABOUT DEFENSIVE CODING
        return null;
    }

    @PostMapping
    ResponseEntity<?> createWorkout(Principal principal, @RequestBody CreateWorkoutDto createWorkoutDto){
        workoutService.createWorkoutWithExercises(principal.getName(), createWorkoutDto);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PostMapping("/{uid}")
    ResponseEntity<?> addExerciseToWorkout(@PathVariable String uid, @RequestBody ExerciseToExistingWorkoutDto exerciseToExistingWorkoutDto){
        workoutService.addExerciseToExistingWorkout(uid, exerciseToExistingWorkoutDto);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
