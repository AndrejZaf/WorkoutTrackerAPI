package com.example.fitnesstracker.domain.user.mapper;

import com.example.fitnesstracker.domain.user.dto.*;
import com.example.fitnesstracker.domain.user.entity.User;
import com.example.fitnesstracker.domain.user.request.UserForgotPasswordEmailRequest;
import com.example.fitnesstracker.domain.user.request.UserRegistrationRequest;
import com.example.fitnesstracker.domain.user.request.UserVerificationEmailRequest;
import com.example.fitnesstracker.domain.user.response.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserRegistrationDto userRegistrationRequestToDto(UserRegistrationRequest userRegisterUserRequest);
    User userRegistrationDtoToUser(UserRegistrationDto userRegistrationDto);
    UserRegistrationDto userToUserRegistrationDto(User user);
    UserRegistrationResponse userRegistrationDtoToResponse(UserRegistrationDto userRegistrationDto);
    UserVerificationResponse userVerificationDtoToResponse(UserVerificationDto userVerificationDto);
    UserVerificationEmailDto userVerificationEmailRequestToDto(UserVerificationEmailRequest userVerificationEmailRequest);
    UserVerificationEmailDto userToUserVerificationEmailDto(User user);
    UserVerificationEmailResponse userVerificationDtoToResponse(UserVerificationEmailDto userVerificationEmailDto);
    UserForgotPasswordEmailDto userForgotPasswordEmailRequestToDto(UserForgotPasswordEmailRequest userForgotPasswordEmailRequest);
    UserForgotPasswordEmailResponse userForgotPasswordEmailDtoToResponse(UserForgotPasswordEmailDto userForgotPasswordEmailDto);
    UserForgotPasswordResponse userForgotPasswordDtoToResponse(UserForgotPasswordDto userForgotPasswordDto);
}
