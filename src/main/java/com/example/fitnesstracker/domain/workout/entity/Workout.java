package com.example.fitnesstracker.domain.workout.entity;

import com.example.fitnesstracker.domain.entity.BaseEntity;
import com.example.fitnesstracker.domain.user.entity.User;
import com.example.fitnesstracker.domain.workoutExercise.entity.WorkoutExercise;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Workout extends BaseEntity {
    private String name;
    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "workout")
    private Set<WorkoutExercise> workoutExercises;
}
