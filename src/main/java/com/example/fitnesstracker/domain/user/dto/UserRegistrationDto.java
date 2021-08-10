package com.example.fitnesstracker.domain.user.dto;

import com.example.fitnesstracker.domain.user.enumeration.MeasurementSystemEnum;
import lombok.Data;

@Data
public class UserRegistrationDto {
    private String email;
    private String password;
    private MeasurementSystemEnum measurementSystem;
}
