package com.example.fitnesstracker.controller;

import com.example.fitnesstracker.domain.exercise.dto.ExerciseDto;
import com.example.fitnesstracker.domain.user.entity.User;
import com.example.fitnesstracker.service.exercise.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${spring.data.rest.base-path}/exercise")
@RequiredArgsConstructor
public class ExerciseController {
    private final ExerciseService exerciseService;

    @GetMapping
    public ResponseEntity<List<ExerciseDto>> getExercises() {

        return new ResponseEntity<>(exerciseService.getExercises(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseDto> getExerciseById(@PathVariable Long id) {
        return new ResponseEntity<>(exerciseService.getExerciseById(id), HttpStatus.OK);
    }

    @GetMapping("/uid/{uid}")
    public ResponseEntity<ExerciseDto> getExerciseByUId(@PathVariable String uid) {
        return new ResponseEntity<>(exerciseService.getExerciseByUid(uid), HttpStatus.OK);
    }
}
