package com.example.fitnesstracker.domain.workout.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateWorkoutDto {
    private String name;
    private List<CreateWorkoutExerciseUidDto> exercises;
}
