package com.example.fitnesstracker.controller;

import com.example.fitnesstracker.domain.workout.WorkoutDTO;
import com.example.fitnesstracker.domain.workout.dto.CreateWorkoutDTO;
import com.example.fitnesstracker.domain.workout.dto.ExerciseToExistingWorkoutDTO;
import com.example.fitnesstracker.domain.workout.entity.Workout;
import com.example.fitnesstracker.domain.workout.response.CreateWorkoutResponse;
import com.example.fitnesstracker.domain.workout.util.WorkoutConverter;
import com.example.fitnesstracker.service.workout.WorkoutService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.fitnesstracker.domain.workout.util.WorkoutConverter.getWorkoutExercises;

@RestController
@RequestMapping("${spring.data.rest.base-path}/workout")
@AllArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @GetMapping
    ResponseEntity<List<WorkoutDTO>> getWorkoutsPerUser(Principal principal){
        List<WorkoutDTO> workouts = workoutService.getWorkoutsForUser(principal.getName())
                .stream().map(WorkoutConverter::getWorkoutExercises)
                .collect(Collectors.toList());
        return new ResponseEntity<>(workouts, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<CreateWorkoutResponse> createWorkout(Principal principal, @RequestBody CreateWorkoutDTO createWorkoutDto){
        String workoutUid = workoutService.createWorkoutWithExercises(principal.getName(), createWorkoutDto);
        return new ResponseEntity<>(new CreateWorkoutResponse(workoutUid), HttpStatus.CREATED);
    }

    @GetMapping("/{uid}")
    ResponseEntity<?> getWorkoutByUid(@PathVariable String uid){
        Workout workout = workoutService.getWorkoutByUid(uid);
        return new ResponseEntity<>(getWorkoutExercises(workout), HttpStatus.OK);
    }

    @PutMapping("/{workoutUid}")
    ResponseEntity<?> updateWorkoutByUid(@PathVariable String workoutUid, @RequestBody CreateWorkoutDTO createWorkoutDto){
        workoutService.updateWorkout(workoutUid, createWorkoutDto);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/{uid}")
    ResponseEntity<?> addExerciseToWorkout(@PathVariable String uid, @RequestBody ExerciseToExistingWorkoutDTO exerciseToExistingWorkoutDto){
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
