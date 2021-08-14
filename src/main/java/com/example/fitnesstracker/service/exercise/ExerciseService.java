package com.example.fitnesstracker.service.exercise;

import com.example.fitnesstracker.domain.exercise.dto.ExerciseDto;
import com.example.fitnesstracker.domain.exercise.entity.Exercise;

import java.util.List;

public interface ExerciseService {
    List<ExerciseDto> getExercises();
    ExerciseDto getExerciseByUid(String uid);
    ExerciseDto getExerciseById(Long id);
}
