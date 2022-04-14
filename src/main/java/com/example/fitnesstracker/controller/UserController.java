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
import com.example.fitnesstracker.domain.user.request.*;
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
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.fitnesstracker.controller.util.WorkoutUtil.sortWorkoutData;
import static com.example.fitnesstracker.domain.user.mapper.CustomUserMapper.convertToUserExtendedDTO;
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

    @GetMapping("/logged-user")
    public ResponseEntity<UserExtendedDTO> getLoggedInUserData(Principal principal) {
        User user = userService.getUser(principal.getName());
        user.setWorkouts(sortWorkoutData(user.getWorkouts()));
        UserExtendedDTO userExtendedDTO = convertToUserExtendedDTO(user);
        return new ResponseEntity<>(userExtendedDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserRegistrationDTO> saveUser(@RequestBody UserRegistrationDTO userRegistrationDto){
        User savedUserDTO = userService.saveUser(userRegistrationDto);
        UserRegistrationDTO savedUserRegistrationDTO = UserMapper.INSTANCE.userToUserRegistrationDto(savedUserDTO);
        return new ResponseEntity<>(savedUserRegistrationDTO, HttpStatus.CREATED);
    }

    @PostMapping("/verify/{code}")
    public ResponseEntity<UserVerificationDTO> verifyUser(@PathVariable(value = "code") String code){
        UserVerificationDTO userVerification = new UserVerificationDTO(code);
        userVerification = userService.verifyUser(userVerification);
        return new ResponseEntity<>(userVerification, HttpStatus.OK);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> requestVerificationEmail(@RequestBody UserVerificationEmailDTO userVerificationEmailDto){
        userService.requestVerificationEmail(userVerificationEmailDto);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/reset-password/{code}")
    public ResponseEntity<?> forgotPasswordValidation(@PathVariable(value = "code") String code, @RequestBody UserForgotPasswordDTO userForgotPasswordRequestDto){
        UserForgotPasswordDTO userForgotPasswordDto = new UserForgotPasswordDTO(code, userForgotPasswordRequestDto.getPassword());
        userService.changeUserPassword(userForgotPasswordDto);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> requestForgotPasswordEmail(@RequestBody UserForgotPasswordEmailDTO userForgotPasswordRequest){
        userService.requestForgotPasswordEmail(userForgotPasswordRequest);
        return new ResponseEntity<>(null, HttpStatus.OK);
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

    @PostMapping("/measurement")
    public ResponseEntity<ChangeMeasurementSystemDTO> saveRoleToUser(Principal principal, @RequestBody ChangeMeasurementSystemRequest request){
        String measurementSystem = userService.updateMeasurementSystem(principal.getName(), request.getMeasurementSystem());
        return new ResponseEntity<>(new ChangeMeasurementSystemDTO(measurementSystem), HttpStatus.OK);
    }
}
