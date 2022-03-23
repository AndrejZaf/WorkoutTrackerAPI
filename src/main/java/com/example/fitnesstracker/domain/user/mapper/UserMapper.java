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
    User userRegistrationDtoToUser(UserRegistrationDTO userRegistrationDto);
    UserRegistrationDTO userToUserRegistrationDto(User user);
}
