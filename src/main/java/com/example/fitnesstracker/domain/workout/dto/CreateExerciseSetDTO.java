package com.example.fitnesstracker.domain.workout.dto;

import lombok.Data;

@Data
public class CreateExerciseSetDTO {

    private Integer reps;

    private Double weight;

    private Integer restPeriod;
}
