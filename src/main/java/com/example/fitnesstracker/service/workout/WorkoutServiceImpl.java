package com.example.fitnesstracker.service.workout;

import com.example.fitnesstracker.domain.exercise.entity.Exercise;
import com.example.fitnesstracker.domain.user.entity.User;
import com.example.fitnesstracker.domain.user.exception.UserNotFoundException;
import com.example.fitnesstracker.domain.workout.dto.CreateWorkoutDto;
import com.example.fitnesstracker.domain.workout.dto.ExerciseToExistingWorkoutDto;
import com.example.fitnesstracker.domain.workout.entity.Workout;
import com.example.fitnesstracker.domain.workoutExercise.entity.WorkoutExercise;
import com.example.fitnesstracker.repository.ExerciseRepository;
import com.example.fitnesstracker.repository.UserRepository;
import com.example.fitnesstracker.repository.WorkoutExerciseRepository;
import com.example.fitnesstracker.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService{
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final UserRepository userRepository;

    @Override
    public void createWorkoutWithExercises(String username, CreateWorkoutDto createWorkoutDto) {
        Workout workout = new Workout();
        workout.setName(createWorkoutDto.getName());

        Set<WorkoutExercise> workoutExercises = new HashSet<>();
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
            workoutExercise.setReps(ex.getReps());
            workoutExercise.setRestPeriod(ex.getRestPeriod());
            workoutExercise.setExercise(exerciseFromDatabase);
            workoutExercise.setWorkout(workout);
            workoutExercise.setUid(UUID.randomUUID());
            workoutExerciseRepository.save(workoutExercise);
            workoutExercises.add(workoutExercise);
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
            workoutExercise.setReps(ex.getReps());
            workoutExercise.setRestPeriod(ex.getRestPeriod());
            workoutExercise.setExercise(exerciseFromDatabase);
            workoutExercise.setWorkout(workout);
            workoutExercise.setUid(UUID.randomUUID());
            workoutExerciseRepository.save(workoutExercise);
            workoutExercises.add(workoutExercise);
        });

        workoutRepository.save(workout);

    }
}
