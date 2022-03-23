package com.example.fitnesstracker.domain.exercise.mapper;

import com.example.fitnesstracker.domain.exercise.dto.ExerciseDTO;
import com.example.fitnesstracker.domain.exercise.entity.Exercise;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExerciseMapper {
    ExerciseMapper INSTANCE = Mappers.getMapper(ExerciseMapper.class);
    ExerciseDTO exerciseEntityToExerciseDto(Exercise e);
}