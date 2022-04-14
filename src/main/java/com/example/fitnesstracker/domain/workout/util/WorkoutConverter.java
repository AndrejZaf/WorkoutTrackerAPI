package com.example.fitnesstracker.domain.workout.util;

import com.example.fitnesstracker.domain.set.dto.ExerciseSetDTO;
import com.example.fitnesstracker.domain.workout.WorkoutDTO;
import com.example.fitnesstracker.domain.workout.entity.Workout;
import com.example.fitnesstracker.domain.workoutExercise.dto.WorkoutExerciseDTO;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class WorkoutConverter {
    public static WorkoutDTO getWorkoutExercises(Workout workout) {
        List<WorkoutExerciseDTO> workoutExerciseResponse = workout.getWorkoutExercises().stream().map(workoutExercises -> {
            List<ExerciseSetDTO> exerciseSetResponse = workoutExercises.getSet().stream().map(exerciseSet ->
                    new ExerciseSetDTO(exerciseSet.getReps(), exerciseSet.getWeight(), exerciseSet.getRestPeriod())).collect(Collectors.toList());

            return new WorkoutExerciseDTO(workoutExercises.getExercise().getUid().toString(), workoutExercises.getExercise().getName(), workoutExercises.getExercise().getImage(), workoutExercises.getExercise().getTarget(), exerciseSetResponse);
        }).collect(Collectors.toList());

        return new WorkoutDTO(workout.getName(), workout.getUid().toString(), workoutExerciseResponse);
    }
}
