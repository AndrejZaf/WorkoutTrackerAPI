package com.example.fitnesstracker.controller;

import com.example.fitnesstracker.domain.workout.WorkoutResponse;
import com.example.fitnesstracker.domain.workout.dto.CreateWorkoutDto;
import com.example.fitnesstracker.domain.workout.dto.ExerciseToExistingWorkoutDto;
import com.example.fitnesstracker.domain.workout.response.CreateWorkoutResponse;
import com.example.fitnesstracker.service.workout.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("${spring.data.rest.base-path}/workout")
@RequiredArgsConstructor
public class WorkoutController {
    private final WorkoutService workoutService;

    @GetMapping
    ResponseEntity<List<WorkoutResponse>> getWorkoutsPerUser(Principal principal){
        // TODO (1): Implement the workouts per user and prepare for clean up so far because the code is getting big
        // TODO (2): REMEMBER ABOUT DEFENSIVE CODING
        return new ResponseEntity<>(workoutService.getWorkoutsForUser(principal.getName()), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<CreateWorkoutResponse> createWorkout(Principal principal, @RequestBody CreateWorkoutDto createWorkoutDto){
        String workoutUid = workoutService.createWorkoutWithExercises(principal.getName(), createWorkoutDto);
        return new ResponseEntity<>(new CreateWorkoutResponse(workoutUid), HttpStatus.CREATED);
    }

    @GetMapping("/{uid}")
    ResponseEntity<?> getWorkoutByUid(@PathVariable String uid){
        WorkoutResponse workoutResponse = workoutService.getWorkoutByUid(uid);
        return new ResponseEntity<>(workoutResponse, HttpStatus.OK);
    }

    @PutMapping("/{workoutUid}")
    ResponseEntity<?> updateWorkoutByUid(@PathVariable String workoutUid, @RequestBody CreateWorkoutDto createWorkoutDto){
        workoutService.updateWorkout(workoutUid, createWorkoutDto);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/{uid}")
    ResponseEntity<?> addExerciseToWorkout(@PathVariable String uid, @RequestBody ExerciseToExistingWorkoutDto exerciseToExistingWorkoutDto){
        workoutService.addExerciseToExistingWorkout(uid, exerciseToExistingWorkoutDto);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/{workoutUid}/{exerciseUid}")
    ResponseEntity<?> removeExerciseFromWorkout(@PathVariable String workoutUid, @PathVariable String exerciseUid){
        workoutService.removeExerciseFromWorkout(workoutUid, exerciseUid);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


    @DeleteMapping("/{uid}")
    ResponseEntity<?> deleteWorkoutByUid(@PathVariable String uid){
        workoutService.deleteWorkoutByUid(uid);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
