package com.example.fitnesstracker.controller;

import com.example.fitnesstracker.domain.exercise.dto.ExerciseDTO;
import com.example.fitnesstracker.domain.exercise.entity.Exercise;
import com.example.fitnesstracker.domain.exercise.mapper.ExerciseMapper;
import com.example.fitnesstracker.service.exercise.ExerciseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${spring.data.rest.base-path}/exercise")
@AllArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @GetMapping
    public ResponseEntity<List<ExerciseDTO>> getExercises() {
        List<ExerciseDTO> exerciseDTOList = exerciseService.getExercises().stream()
                .map(ExerciseMapper.INSTANCE::exerciseEntityToExerciseDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(exerciseDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseDTO> getExerciseById(@PathVariable Long id) {
        Exercise exercise = exerciseService.getExerciseById(id);
        ExerciseDTO exerciseDto = ExerciseMapper.INSTANCE.exerciseEntityToExerciseDto(exercise);
        return new ResponseEntity<>(exerciseDto, HttpStatus.OK);
    }

    @GetMapping("/uid/{uid}")
    public ResponseEntity<ExerciseDTO> getExerciseByUId(@PathVariable String uid) {
        Exercise exercise = exerciseService.getExerciseByUid(uid);
        ExerciseDTO exerciseDto = ExerciseMapper.INSTANCE.exerciseEntityToExerciseDto(exercise);
        return new ResponseEntity<>(exerciseDto, HttpStatus.OK);
    }
}
