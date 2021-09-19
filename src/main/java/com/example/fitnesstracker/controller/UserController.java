package com.example.fitnesstracker.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.fitnesstracker.domain.role.dto.AddRoleToUserDto;
import com.example.fitnesstracker.domain.user.dto.*;
import com.example.fitnesstracker.domain.role.entity.Role;
import com.example.fitnesstracker.domain.user.entity.User;
import com.example.fitnesstracker.domain.user.mapper.UserMapper;
import com.example.fitnesstracker.domain.user.request.UserForgotPasswordEmailRequest;
import com.example.fitnesstracker.domain.user.request.UserForgotPasswordRequest;
import com.example.fitnesstracker.domain.user.request.UserRegistrationRequest;
import com.example.fitnesstracker.domain.user.request.UserVerificationEmailRequest;
import com.example.fitnesstracker.domain.user.response.*;
import com.example.fitnesstracker.service.role.RoleService;
import com.example.fitnesstracker.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("${spring.data.rest.base-path}/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserRegistrationResponse> saveUser(@RequestBody UserRegistrationRequest userRequest){
        UserRegistrationDto userRegistrationDto = UserMapper.INSTANCE.userRegistrationRequestToDto(userRequest);
        userRegistrationDto = userService.saveUser(userRegistrationDto);
        UserRegistrationResponse response = UserMapper.INSTANCE.userRegistrationDtoToResponse(userRegistrationDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/verify/{code}")
    public ResponseEntity<UserVerificationResponse> verifyUser(@PathVariable(value = "code") String code){
        UserVerificationDto userVerification = new UserVerificationDto(code);
        userVerification = userService.verifyUser(userVerification);
        UserVerificationResponse userVerificationResponse = UserMapper.INSTANCE.userVerificationDtoToResponse(userVerification);
        return new ResponseEntity<>(userVerificationResponse, HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<UserVerificationEmailResponse> requestVerificationEmail(@RequestBody UserVerificationEmailRequest userEmailRequest){
        UserVerificationEmailDto userDto = UserMapper.INSTANCE.userVerificationEmailRequestToDto(userEmailRequest);
        userDto = userService.requestVerificationEmail(userDto);
        UserVerificationEmailResponse response = UserMapper.INSTANCE.userVerificationDtoToResponse(userDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/reset-password/{code}")
    public ResponseEntity<UserForgotPasswordResponse> forgotPasswordValidation(@PathVariable(value = "code") String code, @RequestBody UserForgotPasswordRequest userForgotPasswordRequest){
        UserForgotPasswordDto userForgotPasswordDto = new UserForgotPasswordDto(code, userForgotPasswordRequest.getPassword());
        userForgotPasswordDto = userService.changeUserPassword(userForgotPasswordDto);
        UserForgotPasswordResponse userForgotPasswordResponse = UserMapper.INSTANCE.userForgotPasswordDtoToResponse(userForgotPasswordDto);
        return new ResponseEntity<>(userForgotPasswordResponse, HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<UserForgotPasswordEmailResponse> requestForgotPasswordEmail(@RequestBody UserForgotPasswordEmailRequest userForgotPasswordRequest){
        UserForgotPasswordEmailDto userForgotPasswordEmailDto = UserMapper.INSTANCE.userForgotPasswordEmailRequestToDto(userForgotPasswordRequest);
        userForgotPasswordEmailDto = userService.requestForgotPasswordEmail(userForgotPasswordEmailDto);
        UserForgotPasswordEmailResponse response = UserMapper.INSTANCE.userForgotPasswordEmailDtoToResponse(userForgotPasswordEmailDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        Role savedRole = roleService.saveRole(role);
        return new ResponseEntity<>(savedRole, HttpStatus.CREATED);
    }

    @PostMapping("/role/addToUser")
    public ResponseEntity<Role> saveRoleToUser(@RequestBody AddRoleToUserDto addRoleToUserDto){
        userService.addRoleToUser(addRoleToUserDto.getUsername(), addRoleToUserDto.getRolename());
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    // Not sure if this is the correct way to go
    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try{
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); // Refactor this in a util class
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);

                String accessToken = JWT.create()
                        .withSubject(user.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e){
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
//                    response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}
