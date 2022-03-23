package com.example.fitnesstracker.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserForgotPasswordDTO {

    private String code;

    private String password;

    public UserForgotPasswordDTO(String code) {
        this.code = code;
    }

}
