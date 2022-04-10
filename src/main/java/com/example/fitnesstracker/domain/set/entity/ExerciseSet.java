package com.example.fitnesstracker.domain.set.entity;

import com.example.fitnesstracker.domain.entity.BaseEntity;
import com.example.fitnesstracker.domain.workoutExercise.entity.WorkoutExercise;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = {"workoutExercise"})
public class ExerciseSet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exercise_set_id_generator")
    @SequenceGenerator(name = "exercise_set_id_generator", sequenceName = "exercise_set_id_seq", allocationSize = 1)
    private Long id;

    private Integer reps;

    private Double weight;

    private Integer restPeriod;

    @ManyToOne
    @JoinColumn(name ="workout_exercise_id", referencedColumnName = "id")
    private WorkoutExercise workoutExercise;
}
