package com.example.fitnesstracker.domain.workout;

import com.example.fitnesstracker.domain.workoutExercise.dto.WorkoutExerciseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WorkoutDTO {
    String name;

    String uid;

    @JsonProperty("exercises")
    List<WorkoutExerciseDTO> workoutExerciseResponse;
}
