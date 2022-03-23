package com.example.fitnesstracker.domain.workout.dto;

import lombok.Data;

import java.util.List;
@Data
public class ExerciseToExistingWorkoutDTO {

    private List<CreateWorkoutExerciseUidDTO> exercises;
}
