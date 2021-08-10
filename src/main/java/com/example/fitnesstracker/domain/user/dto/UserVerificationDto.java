package com.example.fitnesstracker.domain.user.dto;

import lombok.Data;

@Data
public class UserVerificationDto {
    private String verificationCode;
    private String email;

    public UserVerificationDto(String code) {
        this.verificationCode = code;
    }

    public UserVerificationDto(String verificationCode, String email) {
        this.verificationCode = verificationCode;
        this.email = email;
    }
}
