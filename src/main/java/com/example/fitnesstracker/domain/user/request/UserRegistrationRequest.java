package com.example.fitnesstracker.domain.user.request;

import com.example.fitnesstracker.domain.user.enumeration.MeasurementSystemEnum;
import lombok.Data;

@Data
public class UserRegistrationRequest {
    private String email;
    private String password;
    private MeasurementSystemEnum measurementSystem;
}
