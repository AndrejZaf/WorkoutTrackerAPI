package com.example.fitnesstracker.domain.user.request;

import lombok.Data;

@Data
public class UserVerificationRequest {
    private String verificationCode;
}
