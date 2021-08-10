package com.example.fitnesstracker.domain.exercise.entity;

import com.example.fitnesstracker.domain.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exercise extends BaseEntity {
    private String name;
    private String forceType;
    private String level;
    private String mechanic;
    private String equipment;
    private String primaryMuscles;
    private String description;
    private String category;
    private String secondaryMuscles;
}
