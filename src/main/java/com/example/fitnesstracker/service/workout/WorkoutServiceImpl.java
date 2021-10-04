package com.example.fitnesstracker.service.workout;

import com.example.fitnesstracker.domain.exercise.entity.Exercise;
import com.example.fitnesstracker.domain.exercise.exception.ExerciseNotFoundException;
import com.example.fitnesstracker.domain.set.entity.ExerciseSet;
import com.example.fitnesstracker.domain.set.response.ExerciseSetResponse;
import com.example.fitnesstracker.domain.user.entity.User;
import com.example.fitnesstracker.domain.user.exception.UserNotFoundException;
import com.example.fitnesstracker.domain.workout.WorkoutResponse;
import com.example.fitnesstracker.domain.workout.dto.CreateWorkoutDto;
import com.example.fitnesstracker.domain.workout.dto.ExerciseToExistingWorkoutDto;
import com.example.fitnesstracker.domain.workout.entity.Workout;
import com.example.fitnesstracker.domain.workoutExercise.entity.WorkoutExercise;
import com.example.fitnesstracker.domain.workoutExercise.response.WorkoutExerciseResponse;
import com.example.fitnesstracker.repository.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final UserRepository userRepository;
    private final ExerciseSetRepository exerciseSetRepository;

    @Override
    public String createWorkoutWithExercises(String username, CreateWorkoutDto createWorkoutDto) {
        Workout workout = new Workout();
        workout.setName(createWorkoutDto.getName());

        Set<WorkoutExercise> workoutExercises = new HashSet<>();
        Set<ExerciseSet> exerciseSets = new HashSet<>();
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UserNotFoundException());

        Set<Workout> workouts = user.getWorkouts();
        workouts.add(workout);
        user.setWorkouts(workouts);
        workout.setUser(user);
        workout.setWorkoutExercises(Collections.emptySet());
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

        return workout.getUid().toString();
    }

    @Override
    public String addExerciseToExistingWorkout(String uid, ExerciseToExistingWorkoutDto exerciseToExistingWorkoutDto) {
        Workout workout = workoutRepository.findByUid(UUID.fromString(uid)).orElseThrow(() -> new EntityNotFoundException());

        Set<WorkoutExercise> workoutExercises = workout.getWorkoutExercises();

        exerciseToExistingWorkoutDto.getExercises().forEach(ex -> {
            Exercise exerciseFromDatabase = exerciseRepository.findByUid(UUID.fromString(ex.getUid()))
                    .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
            WorkoutExercise workoutExercise = new WorkoutExercise();
            workoutExercise.setExercise(exerciseFromDatabase);
            workoutExercise.setWorkout(workout);
            workoutExercise.setUid(UUID.randomUUID());
            workoutExerciseRepository.save(workoutExercise);
            workoutExercises.add(workoutExercise);
        });

        workoutRepository.save(workout);
        return workout.getUid().toString();
    }


    @Override
    public List<WorkoutResponse> getWorkoutsForUser(String email) {
        User user = userRepository.findByEmail(email).get();
        return user.getWorkouts().stream().map(workout -> {
            List<WorkoutExerciseResponse> workoutExerciseResponse = workout.getWorkoutExercises().stream().map(workoutExercises -> {
                List<ExerciseSetResponse> exerciseSetResponse = workoutExercises.getSet().stream().map(exerciseSet ->
                        new ExerciseSetResponse(exerciseSet.getReps(), exerciseSet.getWeight(), exerciseSet.getRestPeriod())).collect(Collectors.toList());

                return new WorkoutExerciseResponse(workoutExercises.getExercise().getUid().toString(), exerciseSetResponse);
            }).collect(Collectors.toList());

            WorkoutResponse response = new WorkoutResponse(workout.getName(), workout.getUid().toString(), workoutExerciseResponse);
            return response;
        }).collect(Collectors.toList());
    }


    @Override
    public WorkoutResponse getWorkoutByUid(String uid) {
        Workout workout = workoutRepository.findByUid(UUID.fromString(uid)).orElseThrow(() -> new EntityNotFoundException("Workout not found"));
        List<WorkoutExerciseResponse> workoutExerciseResponse = workout.getWorkoutExercises().stream().map(workoutExercises -> {
            List<ExerciseSetResponse> exerciseSetResponse = workoutExercises.getSet().stream().map(exerciseSet ->
                    new ExerciseSetResponse(exerciseSet.getReps(), exerciseSet.getWeight(), exerciseSet.getRestPeriod())).collect(Collectors.toList());

            return new WorkoutExerciseResponse(workoutExercises.getExercise().getUid().toString(), exerciseSetResponse);
        }).collect(Collectors.toList());

        WorkoutResponse workoutResponse = new WorkoutResponse(workout.getName(), workout.getUid().toString(), workoutExerciseResponse);

        return workoutResponse;
    }

    @Override
    public void removeExerciseFromWorkout(String workoutUid, String exerciseUid) {
        Workout workout = workoutRepository.findByUid(UUID.fromString(workoutUid)).orElseThrow(() -> new EntityNotFoundException("Workout not found"));
        Set<WorkoutExercise> workoutExercises = workout.getWorkoutExercises();
        WorkoutExercise wExercise = workoutExercises.stream()
                .filter(workoutExercise -> workoutExercise.getExercise().getUid().equals(UUID.fromString(exerciseUid)))
                .findFirst()
                .orElseThrow(() -> new ExerciseNotFoundException());
        workoutExerciseRepository.delete(wExercise);
    }

    @Override
    public void deleteWorkoutByUid(String uid) {
        workoutRepository.delete(workoutRepository.findByUid(UUID.fromString(uid)).orElseThrow(() -> new EntityNotFoundException("Workout not found")));
    }

    @Override
    public void updateWorkout(String workoutUid, CreateWorkoutDto createWorkoutDto) {
        Workout workout = workoutRepository.findByUid(UUID.fromString(workoutUid)).orElseThrow(() -> new EntityNotFoundException("Workout not found"));
        workout.setName(createWorkoutDto.getName());
        Set<WorkoutExercise> workoutExercises = workout.getWorkoutExercises();
        Set<ExerciseSet> exerciseSets = new HashSet<>();

        workoutExercises.forEach(workoutExercise -> {
            exerciseSetRepository.deleteAllInBatch(workoutExercise.getSet());
        });
        workoutExerciseRepository.deleteAllInBatch(workoutExercises);

        workout.setWorkoutExercises(Collections.emptySet());
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
    }
}
