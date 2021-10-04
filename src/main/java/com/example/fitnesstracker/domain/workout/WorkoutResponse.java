package com.example.fitnesstracker.domain.workout;

import com.example.fitnesstracker.domain.workoutExercise.response.WorkoutExerciseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WorkoutResponse {
    String name;
    String uid;
    @JsonProperty("exercises")
    List<WorkoutExerciseResponse> workoutExerciseResponse;
}
