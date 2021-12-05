package com.example.fitnesstracker.service.exercise;

import com.example.fitnesstracker.domain.exercise.dto.ExerciseDto;
import com.example.fitnesstracker.domain.exercise.entity.Exercise;
import com.example.fitnesstracker.domain.exercise.exception.ExerciseNotFoundException;
import com.example.fitnesstracker.domain.exercise.mapper.ExerciseMapper;
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
    public List<ExerciseDto> getExercises() {
        return exerciseRepository.findAll()
                .stream()
                .map(ExerciseMapper.INSTANCE::exerciseEntityToExerciseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ExerciseDto getExerciseByUid(String uid) {
        Exercise ex = exerciseRepository.findByUid(UUID.fromString(uid)).orElseThrow(ExerciseNotFoundException::new);
        return ExerciseMapper.INSTANCE.exerciseEntityToExerciseDto(ex);
    }

    @Override
    public ExerciseDto getExerciseById(Long id) {
        Exercise ex = exerciseRepository.findById(id).orElseThrow(ExerciseNotFoundException::new);
        return ExerciseMapper.INSTANCE.exerciseEntityToExerciseDto(ex);
    }
}
