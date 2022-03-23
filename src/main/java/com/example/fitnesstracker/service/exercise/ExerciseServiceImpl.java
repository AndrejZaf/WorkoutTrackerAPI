package com.example.fitnesstracker.service.exercise;

import com.example.fitnesstracker.domain.exercise.entity.Exercise;
import com.example.fitnesstracker.domain.exercise.exception.ExerciseNotFoundException;
import com.example.fitnesstracker.repository.ExerciseRepository;
import lombok.AllArgsConstructor;
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
    public Exercise getExerciseByUid(String uid) {
        return exerciseRepository.findByUid(UUID.fromString(uid)).orElseThrow(ExerciseNotFoundException::new);
    }

    @Override
    public Exercise getExerciseById(Long id) {
        return exerciseRepository.findById(id).orElseThrow(ExerciseNotFoundException::new);
    }
}
