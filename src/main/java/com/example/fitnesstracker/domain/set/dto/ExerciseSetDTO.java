package com.example.fitnesstracker.domain.set.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExerciseSetDTO {
    private Integer reps;

    private Double weight;

    private Integer restPeriod;

}
