package com.example.fitnesstracker.domain.set.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExerciseSetResponse {
    private Integer reps;
    private Double weight;
    private Integer restPeriod;
}
