package com.example.fitnesstracker.service.email;

public interface EmailService {
    void sendVerificationEmail(String email, String verificationCode);
    void sendForgotPasswordEmail(String email, String verification);
}
