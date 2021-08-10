package com.example.fitnesstracker.service.user;

import com.example.fitnesstracker.domain.user.dto.*;
import com.example.fitnesstracker.domain.user.entity.User;

import java.util.List;

public interface UserService {
    UserRegistrationDto saveUser(UserRegistrationDto UserRegistrationDto);
    void addRoleToUser(String username, String rolename);
    User getUser(String username);
    List<User> getUsers();
    UserVerificationDto verifyUser(UserVerificationDto userVerificationDto);
    UserVerificationEmailDto requestVerificationEmail(UserVerificationEmailDto userVerificationEmailDto);
    UserForgotPasswordEmailDto requestForgotPasswordEmail(UserForgotPasswordEmailDto userForgotPasswordEmailDto);
    UserForgotPasswordDto changeUserPassword(UserForgotPasswordDto userForgotPasswordDto);
}
