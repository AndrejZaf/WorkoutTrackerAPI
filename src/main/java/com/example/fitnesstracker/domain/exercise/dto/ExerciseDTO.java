package com.example.fitnesstracker.domain.exercise.dto;

import com.example.fitnesstracker.domain.exercise.enumeration.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ExerciseDTO {

    private LocalDateTime createdAt;

    private UUID uid;

    private String name;

    private BodyPart bodyPart;

    private Target target;

    private Equipment equipment;

}