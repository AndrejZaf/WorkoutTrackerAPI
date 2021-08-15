package com.example.fitnesstracker.domain.workout.dto;

import lombok.Data;

@Data
public class CreateWorkoutExerciseUidDto {
    private String uid;
    private Integer reps;
    private Integer restPeriod;
}
