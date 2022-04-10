package com.example.fitnesstracker.domain.workout.entity;

import com.example.fitnesstracker.domain.entity.BaseEntity;
import com.example.fitnesstracker.domain.user.entity.User;
import com.example.fitnesstracker.domain.workoutExercise.entity.WorkoutExercise;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"workoutExercises"})
public class Workout extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workout_id_generator")
    @SequenceGenerator(name = "workout_id_generator", sequenceName = "workout_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "workout")
    private Set<WorkoutExercise> workoutExercises;
}
