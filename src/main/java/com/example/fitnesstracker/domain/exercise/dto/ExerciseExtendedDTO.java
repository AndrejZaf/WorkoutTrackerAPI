package com.example.fitnesstracker.domain.exercise.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ExerciseExtendedDTO {

    private LocalDateTime createdAt;

    private UUID uid;

    private String name;

    private String bodyPart;

    private String target;

    private String equipment;

    private String image;
}