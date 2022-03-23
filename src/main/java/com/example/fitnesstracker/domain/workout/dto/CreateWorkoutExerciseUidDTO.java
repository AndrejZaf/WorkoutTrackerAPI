package com.example.fitnesstracker.domain.workout.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateWorkoutExerciseUidDTO {

    private String uid;

    private List<CreateExerciseSetDTO> sets;
}
