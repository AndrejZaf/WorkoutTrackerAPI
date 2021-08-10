package com.example.fitnesstracker.domain.user.response;

import lombok.Data;

@Data
public class UserVerificationResponse {
    private String email;
    private String verificationCode;
}
