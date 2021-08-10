package com.example.fitnesstracker.domain.user.dto;

import lombok.Data;

@Data
public class UserForgotPasswordDto {
    private String code;
    private String password;

    public UserForgotPasswordDto(String code, String password) {
        this.code = code;
        this.password = password;
    }

    public UserForgotPasswordDto(String code) {
        this.code = code;
    }
}
