package com.example.fitnesstracker.domain.workoutExercise.dto;

import com.example.fitnesstracker.domain.set.dto.ExerciseSetDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WorkoutExerciseDTO {
    private String uid;

    private List<ExerciseSetDTO> sets;
}
