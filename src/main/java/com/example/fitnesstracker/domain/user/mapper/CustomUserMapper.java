package com.example.fitnesstracker.domain.user.mapper;

import com.example.fitnesstracker.domain.user.dto.UserExtendedDTO;
import com.example.fitnesstracker.domain.user.entity.User;
import com.example.fitnesstracker.domain.workout.util.WorkoutConverter;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

@UtilityClass
public class CustomUserMapper {
    public static UserExtendedDTO convertToUserExtendedDTO(User user) {
        return UserExtendedDTO.builder()
                .email(user.getEmail())
                .uid(user.getUid())
                .height(user.getHeight())
                .weight(user.getWeight())
                .measurementSystem(user.getMeasurementSystem().toString())
                .imageUrl(user.getImageUrl())
                .workouts(user.getWorkouts().stream().map(WorkoutConverter::getWorkoutExercises).collect(Collectors.toList()))
                .build();

    }
}
