package com.example.fitnesstracker.domain.workout.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateWorkoutExerciseUidDto {
    private String uid;
    private List<CreateExerciseSetDto> sets;
}
