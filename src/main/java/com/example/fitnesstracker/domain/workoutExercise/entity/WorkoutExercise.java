package com.example.fitnesstracker.domain.workoutExercise.entity;

import com.example.fitnesstracker.domain.entity.BaseEntity;
import com.example.fitnesstracker.domain.exercise.entity.Exercise;
import com.example.fitnesstracker.domain.set.entity.ExerciseSet;
import com.example.fitnesstracker.domain.workout.entity.Workout;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class WorkoutExercise extends BaseEntity {

    @OneToMany(mappedBy = "workoutExercise")
    private Set<ExerciseSet> set;

    @ManyToOne
    @JoinColumn(name="workout_id", nullable = false)
    private Workout workout;

    @OneToOne
    @JoinColumn(name ="exercise_id")
    private Exercise exercise;
}
