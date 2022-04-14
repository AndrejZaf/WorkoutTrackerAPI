package com.example.fitnesstracker.service.workout;

import com.example.fitnesstracker.domain.exercise.entity.Exercise;
import com.example.fitnesstracker.domain.exercise.exception.ExerciseNotFoundException;
import com.example.fitnesstracker.domain.set.entity.ExerciseSet;
import com.example.fitnesstracker.domain.user.entity.User;
import com.example.fitnesstracker.domain.user.exception.UserNotFoundException;
import com.example.fitnesstracker.domain.workout.dto.CreateWorkoutDTO;
import com.example.fitnesstracker.domain.workout.dto.ExerciseToExistingWorkoutDTO;
import com.example.fitnesstracker.domain.workout.entity.Workout;
import com.example.fitnesstracker.domain.workoutExercise.entity.WorkoutExercise;
import com.example.fitnesstracker.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
@AllArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;

    private final ExerciseRepository exerciseRepository;

    private final WorkoutExerciseRepository workoutExerciseRepository;

    private final UserRepository userRepository;

    private final ExerciseSetRepository exerciseSetRepository;

    @Override
    public String createWorkoutWithExercises(String username, CreateWorkoutDTO createWorkoutDto) {
        Workout workout = new Workout();
        workout.setName(createWorkoutDto.getName());

        List<WorkoutExercise> workoutExercises = new ArrayList<>();
        List<ExerciseSet> exerciseSets = new ArrayList<>();
        User user = userRepository.findByEmail(username).orElseThrow(UserNotFoundException::new);

        List<Workout> workouts = user.getWorkouts();
        workouts.add(workout);
        user.setWorkouts(workouts);
        workout.setUser(user);
        createWorkout(createWorkoutDto, workout, workoutExercises, exerciseSets);
        userRepository.save(user);

        return workout.getUid().toString();
    }

    @Override
    public String addExerciseToExistingWorkout(String uid, ExerciseToExistingWorkoutDTO exerciseToExistingWorkoutDto) {
        Workout workout = workoutRepository.findByUid(UUID.fromString(uid)).orElseThrow(EntityNotFoundException::new);

        List<WorkoutExercise> workoutExercises = workout.getWorkoutExercises();
        List<ExerciseSet> exerciseSets = new ArrayList<>();
        exerciseToExistingWorkoutDto.getExercises().forEach(ex -> {
            Exercise exerciseFromDatabase = exerciseRepository.findByUid(UUID.fromString(ex.getUid()))
                    .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
            WorkoutExercise workoutExercise = new WorkoutExercise();
            workoutExercise.setExercise(exerciseFromDatabase);
            workoutExercise.setWorkout(workout);
            workoutExercise.setUid(UUID.randomUUID());
            workoutExerciseRepository.save(workoutExercise);
            ex.getSets().forEach(set -> {
                ExerciseSet exerciseSet = new ExerciseSet();
                exerciseSet.setUid(UUID.randomUUID());
                exerciseSet.setRestPeriod(set.getRestPeriod());
                exerciseSet.setReps(set.getReps());
                exerciseSet.setWeight(set.getWeight());
                exerciseSet.setWorkoutExercise(workoutExercise);
                exerciseSet = exerciseSetRepository.save(exerciseSet);
                exerciseSets.add(exerciseSet);
            });
            workoutExercises.add(workoutExercise);
        });

        workoutRepository.save(workout);
        return workout.getUid().toString();
    }


    @Override
    public List<Workout> getWorkoutsForUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()) {
            return user.get().getWorkouts();
        }
        throw new UserNotFoundException();
    }


    @Override
    public Workout getWorkoutByUid(String uid) {
        return workoutRepository.findByUid(UUID.fromString(uid)).orElseThrow(() -> new EntityNotFoundException("Workout not found"));
    }

    @Override
    public void removeExerciseFromWorkout(String workoutUid, String exerciseUid) {
        Workout workout = workoutRepository.findByUid(UUID.fromString(workoutUid)).orElseThrow(() -> new EntityNotFoundException("Workout not found"));
        List<WorkoutExercise> workoutExercises = workout.getWorkoutExercises();
        WorkoutExercise wExercise = workoutExercises.stream()
                .filter(workoutExercise -> workoutExercise.getExercise().getUid().equals(UUID.fromString(exerciseUid)))
                .findFirst()
                .orElseThrow(ExerciseNotFoundException::new);
        workoutExerciseRepository.delete(wExercise);
    }

    @Override
    public void deleteWorkoutByUid(String uid) {
        Workout workout = workoutRepository.findByUid(UUID.fromString(uid)).orElseThrow(() -> new EntityNotFoundException("Workout not found"));
        workout.getWorkoutExercises().forEach(workoutExercise -> {
            workoutExercise.getSet().forEach(exerciseSetRepository::delete);
            workoutExerciseRepository.delete(workoutExercise);
        });
        workoutRepository.delete(workout);
    }

    @Override
    public void updateWorkout(String workoutUid, CreateWorkoutDTO createWorkoutDto) {
        Workout workout = workoutRepository.findByUid(UUID.fromString(workoutUid)).orElseThrow(() -> new EntityNotFoundException("Workout not found"));
        workout.setName(createWorkoutDto.getName());
        List<WorkoutExercise> workoutExercises = workout.getWorkoutExercises();
        List<ExerciseSet> exerciseSets = new ArrayList<>();

        workoutExercises.forEach(workoutExercise -> {
            exerciseSetRepository.deleteAllInBatch(workoutExercise.getSet());
        });
        workoutExerciseRepository.deleteAllInBatch(workoutExercises);

        createWorkout(createWorkoutDto, workout, workoutExercises, exerciseSets);
    }

    private void createWorkout(CreateWorkoutDTO createWorkoutDto, Workout workout, List<WorkoutExercise> workoutExercises, List<ExerciseSet> exerciseSets) {
        workout.setWorkoutExercises(Collections.emptyList());
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
                exerciseSet = exerciseSetRepository.save(exerciseSet);
                exerciseSets.add(exerciseSet);
            });
            workoutExercise.setSet(exerciseSets);
            workoutExerciseRepository.save(workoutExercise);
        });

        workout.setWorkoutExercises(workoutExercises);
        workoutRepository.save(workout);
    }
}
