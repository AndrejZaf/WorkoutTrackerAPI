package com.example.fitnesstracker.domain.user.entity;

import com.example.fitnesstracker.domain.entity.BaseEntity;
import com.example.fitnesstracker.domain.role.entity.Role;
import com.example.fitnesstracker.domain.user.enumeration.MeasurementSystemEnum;

import com.example.fitnesstracker.domain.workout.entity.Workout;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    private boolean enabled;

    @NotNull
    @Column(columnDefinition = "VARCHAR(255)")
    @Type(type = "uuid-char")
    private UUID verificationCode;
    @NotNull
    private LocalDateTime verificationExpiresOn;

    @NotNull
    @Column(columnDefinition = "VARCHAR(255)")
    @Type(type = "uuid-char")
    private UUID forgotPasswordCode;
    @NotNull
    private LocalDateTime forgotPasswordCodeExpiresOn;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private MeasurementSystemEnum measurementSystem;

    private Integer weight;

    private Integer height;

    @OneToMany(mappedBy = "user")
    private Set<Workout> workouts;
}
