package com.example.fitnesstracker.domain.exercise.entity;

import com.example.fitnesstracker.domain.entity.BaseEntity;
import com.example.fitnesstracker.domain.exercise.enumeration.*;
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

//    @Enumerated(value = EnumType.STRING)
    private String bodyPart;

//    @Enumerated(value = EnumType.STRING)
    private String equipment;

//    @Enumerated(value = EnumType.STRING)
    private String target;

    private String image;
}
