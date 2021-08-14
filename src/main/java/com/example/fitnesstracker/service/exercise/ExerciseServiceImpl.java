package com.example.fitnesstracker.service.exercise;

import com.example.fitnesstracker.domain.exercise.dto.ExerciseDto;
import com.example.fitnesstracker.domain.exercise.entity.Exercise;
import com.example.fitnesstracker.domain.exercise.exception.ExerciseNotFoundException;
import com.example.fitnesstracker.domain.exercise.mapper.ExerciseMapper;
import com.example.fitnesstracker.repository.ExerciseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ExerciseServiceImpl implements ExerciseService {
    private final ExerciseRepository exerciseRepository;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public List<ExerciseDto> getExercises() {
        List<ExerciseDto> exercises = exerciseRepository.findAll()
                .stream()
                .map(exercise -> ExerciseMapper.INSTANCE.exerciseEntityToExerciseDto(exercise))
                .collect(Collectors.toList());
        return exercises;
    }

    @Override
    public ExerciseDto getExerciseByUid(String uid) {
        Exercise ex = exerciseRepository.findByUid(UUID.fromString(uid)).orElseThrow(() -> new ExerciseNotFoundException());
        return ExerciseMapper.INSTANCE.exerciseEntityToExerciseDto(ex);
    }

    @Override
    public ExerciseDto getExerciseById(Long id) {
        Exercise ex = exerciseRepository.findById(id).orElseThrow(() -> new ExerciseNotFoundException());
        return ExerciseMapper.INSTANCE.exerciseEntityToExerciseDto(ex);
    }
}
