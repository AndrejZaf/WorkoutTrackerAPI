package com.example.fitnesstracker.domain.workoutExercise.entity;

import com.example.fitnesstracker.domain.entity.BaseEntity;
import com.example.fitnesstracker.domain.exercise.entity.Exercise;
import com.example.fitnesstracker.domain.user.entity.User;
import com.example.fitnesstracker.domain.workout.entity.Workout;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class WorkoutExercise extends BaseEntity {
    private int reps;
    private int restPeriod;

    @ManyToOne
    @JoinColumn(name="workout_id", nullable = false)
    private Workout workout;

    @OneToOne
    @JoinColumn(name ="exercise_id", referencedColumnName = "id")
    private Exercise exercise;
}
