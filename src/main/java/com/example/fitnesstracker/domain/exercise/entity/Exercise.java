package com.example.fitnesstracker.domain.exercise.entity;

import com.example.fitnesstracker.domain.entity.BaseEntity;
import com.example.fitnesstracker.domain.exercise.enumeration.*;
import com.example.fitnesstracker.domain.workoutExercise.entity.WorkoutExercise;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exercise extends BaseEntity {
    private String name;
    @Enumerated(value = EnumType.STRING)
    private Force forceType;
    @Enumerated(value = EnumType.STRING)
    private Level level;
    @Enumerated(value = EnumType.STRING)
    private Mechanic mechanic;
    @Enumerated(value = EnumType.STRING)
    private Equipment equipment;
    @Enumerated(value = EnumType.STRING)
    private PrimaryMuscle primaryMuscles;
    private String description;
    @Enumerated(value = EnumType.STRING)
    private Category category;
}
