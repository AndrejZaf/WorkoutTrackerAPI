package com.example.fitnesstracker.repository;

import com.example.fitnesstracker.domain.exercise.entity.Exercise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Optional<Exercise> findByUid(UUID uid);

    Page<Exercise> findAllByTarget(Pageable pageable, String target);

    Page<Exercise> findAllByNameContainingIgnoreCase(Pageable pageable, String exerciseName);

    Page<Exercise> findAllByTargetAndNameContainingIgnoreCase(Pageable pageable, String target, String exerciseName);

    List<Exercise> findAllByTarget(String target);
}
