package com.example.fitnesstracker.domain.workout.dto;

import lombok.Data;

import java.util.List;
@Data
public class ExerciseToExistingWorkoutDto {
    private List<CreateWorkoutExerciseUidDto> exercises;
}
