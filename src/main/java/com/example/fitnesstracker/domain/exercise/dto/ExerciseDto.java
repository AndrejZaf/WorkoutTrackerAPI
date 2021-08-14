package com.example.fitnesstracker.domain.exercise.dto;

import com.example.fitnesstracker.domain.exercise.enumeration.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ExerciseDto {
    private LocalDateTime createdAt;
    private UUID uid;
    private String name;
    private Force forceType;
    private Level level;
    private Mechanic mechanic;
    private Equipment equipment;
    private PrimaryMuscle primaryMuscles;
    private String description;
    private Category category;
}
