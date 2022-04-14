package com.example.fitnesstracker.domain.user.entity;

import com.example.fitnesstracker.domain.entity.BaseEntity;
import com.example.fitnesstracker.domain.role.entity.Role;
import com.example.fitnesstracker.domain.user.enumeration.MeasurementSystemEnum;

import com.example.fitnesstracker.domain.workout.entity.Workout;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fitness_user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fitness_user_id_generator")
    @SequenceGenerator(name = "fitness_user_id_generator", sequenceName = "fitness_user_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    private boolean enabled = false;

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

    private String imageUrl;

    @OneToMany(mappedBy = "user")
    private List<Workout> workouts;
}
