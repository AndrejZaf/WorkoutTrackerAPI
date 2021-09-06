package com.example.fitnesstracker.repository;

import com.example.fitnesstracker.domain.set.entity.ExerciseSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseSetRepository extends JpaRepository<ExerciseSet, Long> {
}
