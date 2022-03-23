package com.example.fitnesstracker.domain.workout.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateWorkoutDTO {

    private String name;

    private List<CreateWorkoutExerciseUidDTO> exercises;
}
