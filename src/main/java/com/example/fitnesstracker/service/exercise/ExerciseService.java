package com.example.fitnesstracker.service.exercise;

import com.example.fitnesstracker.domain.exercise.entity.Exercise;

import java.util.List;

public interface ExerciseService {

    List<Exercise> getExercises();

    Exercise getExerciseByUid(String uid);

    Exercise getExerciseById(Long id);
}
