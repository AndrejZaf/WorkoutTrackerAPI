package com.example.fitnesstracker.domain.user.dto;

import com.example.fitnesstracker.domain.workout.WorkoutDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UserExtendedDTO {

    private String email;

    private UUID uid;

    private Integer height;

    private Integer weight;

    private String measurementSystem;

    private String imageUrl;

    private List<WorkoutDTO> workouts;
}
