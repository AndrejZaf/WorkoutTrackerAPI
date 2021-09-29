package com.example.fitnesstracker.domain.user.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeMeasurementSystemRequest {
    String measurementSystem;
}
