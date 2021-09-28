package com.example.fitnesstracker.domain.workoutExercise.response;

import com.example.fitnesstracker.domain.set.response.ExerciseSetResponse;
import com.example.fitnesstracker.domain.workout.dto.CreateExerciseSetDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WorkoutExerciseResponse {
    private String uid;
    private List<ExerciseSetResponse> sets;
}
