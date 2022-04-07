package com.example.fitnesstracker.service.exercise;

import com.example.fitnesstracker.domain.exercise.entity.Exercise;
import com.example.fitnesstracker.domain.exercise.exception.ExerciseNotFoundException;
import com.example.fitnesstracker.repository.ExerciseRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Override
    public List<Exercise> getExercises() {
        return exerciseRepository.findAll()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public List<Exercise> getExercisesByMuscleGroup(String muscleGroup) {
        return exerciseRepository.findAllByTarget(muscleGroup);
    }

    @Override
    public Page<Exercise> getExercisesPage(Pageable pageable) {
        return exerciseRepository.findAll(pageable);
    }

    @Override
    public Page<Exercise> getExercisesPageByMuscleGroup(Pageable pageable, String muscleGroup) {
        return exerciseRepository.findAllByTarget(pageable, muscleGroup);
    }

    @Override
    public Page<Exercise> getExercisesPageByExerciseName(Pageable pageable, String exerciseName) {
        return exerciseRepository.findAllByNameContainingIgnoreCase(pageable,exerciseName);
    }

    @Override
    public Page<Exercise> getExercisesPageByExerciseNameAndMuscleGroup(Pageable pageable, String exerciseName, String muscleGroup) {
        return exerciseRepository.findAllByTargetAndNameContainingIgnoreCase(pageable, exerciseName, muscleGroup);
    }

    @Override
    public Exercise getExerciseByUid(String uid) {
        return exerciseRepository.findByUid(UUID.fromString(uid)).orElseThrow(ExerciseNotFoundException::new);
    }

    @Override
    public Exercise getExerciseById(Long id) {
        return exerciseRepository.findById(id).orElseThrow(ExerciseNotFoundException::new);
    }
}
