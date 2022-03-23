package com.example.fitnesstracker.service.user;

import com.example.fitnesstracker.domain.user.dto.*;
import com.example.fitnesstracker.domain.user.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(UserRegistrationDTO UserRegistrationDto);
    void addRoleToUser(String username, String rolename);
    User getUser(String username);
    List<User> getUsers();
    UserVerificationDTO verifyUser(UserVerificationDTO userVerificationDto);
    void requestVerificationEmail(UserVerificationEmailDTO userVerificationEmailDto);
    void requestForgotPasswordEmail(UserForgotPasswordEmailDTO userForgotPasswordEmailDto);
    void changeUserPassword(UserForgotPasswordDTO userForgotPasswordDto);
    String updateMeasurementSystem(String email, String measurementSystem);
}
