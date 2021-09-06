package com.example.fitnesstracker.service.workout;

import com.example.fitnesstracker.domain.exercise.entity.Exercise;
import com.example.fitnesstracker.domain.set.entity.ExerciseSet;
import com.example.fitnesstracker.domain.user.entity.User;
import com.example.fitnesstracker.domain.user.exception.UserNotFoundException;
import com.example.fitnesstracker.domain.workout.dto.CreateWorkoutDto;
import com.example.fitnesstracker.domain.workout.dto.ExerciseToExistingWorkoutDto;
import com.example.fitnesstracker.domain.workout.entity.Workout;
import com.example.fitnesstracker.domain.workoutExercise.entity.WorkoutExercise;
import com.example.fitnesstracker.repository.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService{
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final UserRepository userRepository;
    private final ExerciseSetRepository exerciseSetRepository;

    @Override
    public void createWorkoutWithExercises(String username, CreateWorkoutDto createWorkoutDto) {
        Workout workout = new Workout();
        workout.setName(createWorkoutDto.getName());

        Set<WorkoutExercise> workoutExercises = new HashSet<>();
        Set<ExerciseSet> exerciseSets = new HashSet<>();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UserNotFoundException());

        Set<Workout> workouts = user.getWorkouts();
        workouts.add(workout);
        user.setWorkouts(workouts);
        workout.setUser(user);

        workoutRepository.save(workout);
        createWorkoutDto.getExercises().forEach(ex -> {
            Exercise exerciseFromDatabase = exerciseRepository.findByUid(UUID.fromString(ex.getUid()))
                    .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
            WorkoutExercise workoutExercise = new WorkoutExercise();
            workoutExercise.setExercise(exerciseFromDatabase);
            workoutExercise.setWorkout(workout);
            workoutExercise.setUid(UUID.randomUUID());

            workoutExerciseRepository.save(workoutExercise);
            workoutExercises.add(workoutExercise);
            ex.getSets().forEach(set -> {
                ExerciseSet exerciseSet = new ExerciseSet();
                exerciseSet.setUid(UUID.randomUUID());
                exerciseSet.setRestPeriod(set.getRestPeriod());
                exerciseSet.setReps(set.getReps());
                exerciseSet.setWeight(set.getWeight());
                exerciseSet.setWorkoutExercise(workoutExercise);
                exerciseSetRepository.save(exerciseSet);
                exerciseSets.add(exerciseSet);
            });
            workoutExercise.setSet(exerciseSets);
            workoutExerciseRepository.save(workoutExercise);
        });

        workout.setWorkoutExercises(workoutExercises);
        workoutRepository.save(workout);
        userRepository.save(user);

    }

    @Override
    public void addExerciseToExistingWorkout(String uid, ExerciseToExistingWorkoutDto exerciseToExistingWorkoutDto) {
        Workout workout = workoutRepository.findByUid(UUID.fromString(uid)).orElseThrow(() -> new EntityNotFoundException());

        Set<WorkoutExercise> workoutExercises = workout.getWorkoutExercises();

        exerciseToExistingWorkoutDto.getExercises().forEach(ex -> {
            Exercise exerciseFromDatabase = exerciseRepository.findByUid(UUID.fromString(ex.getUid()))
                    .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
            WorkoutExercise workoutExercise = new WorkoutExercise();
//            workoutExercise.setReps(ex.getReps());
//            workoutExercise.setRestPeriod(ex.getRestPeriod());
            workoutExercise.setExercise(exerciseFromDatabase);
            workoutExercise.setWorkout(workout);
            workoutExercise.setUid(UUID.randomUUID());
            workoutExerciseRepository.save(workoutExercise);
            workoutExercises.add(workoutExercise);
        });

        workoutRepository.save(workout);

    }
}
