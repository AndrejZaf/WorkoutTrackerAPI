package com.example.fitnesstracker.controller;

import com.example.fitnesstracker.domain.exercise.dto.ExerciseDTO;
import com.example.fitnesstracker.domain.exercise.dto.ExerciseExtendedDTO;
import com.example.fitnesstracker.domain.exercise.entity.Exercise;
import com.example.fitnesstracker.domain.exercise.mapper.ExerciseMapper;
import com.example.fitnesstracker.service.exercise.ExerciseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RestController
@RequestMapping("${spring.data.rest.base-path}/exercise")
@AllArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @GetMapping
    public ResponseEntity<List<ExerciseDTO>> getExercises(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size, @RequestParam(required = false) String muscleGroup, @RequestParam(required = false) String exerciseName) {
        if (nonNull(page) && nonNull(size)) {
            PageRequest pageRequest = PageRequest.of(page, size);
            List<ExerciseDTO> exercises;
            if(isNull(muscleGroup) && isNull(exerciseName)) {
                exercises = exerciseService.getExercisesPage(pageRequest).get()
                        .map(ExerciseMapper.INSTANCE::exerciseEntityToExerciseDto)
                        .collect(Collectors.toList());
            } else if (isNull(exerciseName)) {
                exercises = exerciseService.getExercisesPageByMuscleGroup(pageRequest, muscleGroup).get()
                        .map(ExerciseMapper.INSTANCE::exerciseEntityToExerciseDto)
                        .collect(Collectors.toList());
            } else if (isNull(muscleGroup)) {
                exercises = exerciseService.getExercisesPageByExerciseName(pageRequest, exerciseName).get()
                        .map(ExerciseMapper.INSTANCE::exerciseEntityToExerciseDto)
                        .collect(Collectors.toList());
            } else {
                exercises = exerciseService.getExercisesPageByExerciseNameAndMuscleGroup(pageRequest, exerciseName, muscleGroup).get()
                        .map(ExerciseMapper.INSTANCE::exerciseEntityToExerciseDto)
                        .collect(Collectors.toList());
            }

            return new ResponseEntity<>(exercises, HttpStatus.OK);
        } else {
            List<ExerciseDTO> exerciseDTOList = isNull(muscleGroup) ?
                    exerciseService.getExercises().stream()
                            .map(ExerciseMapper.INSTANCE::exerciseEntityToExerciseDto)
                            .collect(Collectors.toList()) :
                    exerciseService.getExercisesByMuscleGroup(muscleGroup).stream()
                            .map(ExerciseMapper.INSTANCE::exerciseEntityToExerciseDto)
                            .collect(Collectors.toList());
            return new ResponseEntity<>(exerciseDTOList, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseDTO> getExerciseById(@PathVariable Long id) {
        Exercise exercise = exerciseService.getExerciseById(id);
        ExerciseDTO exerciseDto = ExerciseMapper.INSTANCE.exerciseEntityToExerciseDto(exercise);
        return new ResponseEntity<>(exerciseDto, HttpStatus.OK);
    }

    @GetMapping("/uid/{uid}")
    public ResponseEntity<ExerciseExtendedDTO> getExerciseByUId(@PathVariable String uid) {
        Exercise exercise = exerciseService.getExerciseByUid(uid);
        ExerciseExtendedDTO exerciseDto = ExerciseMapper.INSTANCE.exerciseEntityToExerciseExtendedDto(exercise);
        return new ResponseEntity<>(exerciseDto, HttpStatus.OK);
    }
}
