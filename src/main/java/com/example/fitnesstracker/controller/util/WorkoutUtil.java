package com.example.fitnesstracker.controller.util;

import com.example.fitnesstracker.domain.entity.BaseEntity;
import com.example.fitnesstracker.domain.workout.entity.Workout;
import lombok.experimental.UtilityClass;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class WorkoutUtil {
    public static List<Workout> sortWorkoutData(List<Workout> workouts) {
        workouts.forEach(workout -> {
            workout.setWorkoutExercises(workout.getWorkoutExercises()
                    .stream()
                    .sorted(Comparator.comparing(BaseEntity::getCreatedAt))
                    .collect(Collectors.toList()));

            workout.getWorkoutExercises().forEach(workoutExercise ->
                    workoutExercise.setSet(workoutExercise.getSet()
                            .stream().sorted(Comparator.comparing(BaseEntity::getCreatedAt))
                            .collect(Collectors.toList())));
        });

        return workouts.stream().sorted(Comparator.comparing(BaseEntity::getCreatedAt)).collect(Collectors.toList());
    }
}
