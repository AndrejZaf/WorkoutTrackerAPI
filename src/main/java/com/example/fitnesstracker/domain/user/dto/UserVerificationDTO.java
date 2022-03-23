package com.example.fitnesstracker.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserVerificationDTO {

    private String verificationCode;

    private String email;

    public UserVerificationDTO(String code) {
        this.verificationCode = code;
    }

}
