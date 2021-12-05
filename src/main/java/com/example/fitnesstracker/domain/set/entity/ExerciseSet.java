package com.example.fitnesstracker.domain.set.entity;

import com.example.fitnesstracker.domain.entity.BaseEntity;
import com.example.fitnesstracker.domain.workoutExercise.entity.WorkoutExercise;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
public class ExerciseSet extends BaseEntity {

    private Integer reps;

    private Double weight;

    private Integer restPeriod;

    @ManyToOne
    @JoinColumn(name ="workout_exercise_id", referencedColumnName = "id")
    private WorkoutExercise workoutExercise;
}
