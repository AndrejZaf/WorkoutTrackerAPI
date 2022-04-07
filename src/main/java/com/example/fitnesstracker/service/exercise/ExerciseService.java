package com.example.fitnesstracker.service.exercise;

import com.example.fitnesstracker.domain.exercise.entity.Exercise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExerciseService {

    List<Exercise> getExercises();

    List<Exercise> getExercisesByMuscleGroup(String muscleGroup);

    Page<Exercise> getExercisesPage(Pageable pageable);

    Page<Exercise> getExercisesPageByMuscleGroup(Pageable pageable, String muscleGroup);

    Page<Exercise> getExercisesPageByExerciseName(Pageable pageable, String exerciseName);

    Page<Exercise> getExercisesPageByExerciseNameAndMuscleGroup(Pageable pageable, String exerciseName, String muscleGroup);

    Exercise getExerciseByUid(String uid);

    Exercise getExerciseById(Long id);
}
