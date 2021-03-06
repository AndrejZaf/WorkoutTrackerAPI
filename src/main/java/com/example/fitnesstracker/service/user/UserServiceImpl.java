package com.example.fitnesstracker.service.user;

import com.example.fitnesstracker.domain.role.enumeration.RoleEnum;
import com.example.fitnesstracker.domain.role.exception.RoleNotFoundException;
import com.example.fitnesstracker.domain.user.dto.*;
import com.example.fitnesstracker.domain.role.entity.Role;
import com.example.fitnesstracker.domain.user.entity.User;
import com.example.fitnesstracker.domain.user.enumeration.MeasurementSystemEnum;
import com.example.fitnesstracker.domain.user.exception.*;
import com.example.fitnesstracker.domain.user.mapper.UserMapper;
import com.example.fitnesstracker.repository.RoleRepository;
import com.example.fitnesstracker.repository.UserRepository;
import com.example.fitnesstracker.service.email.EmailService;
import com.example.fitnesstracker.service.email.EmailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public User saveUser(UserRegistrationDTO userRegistrationDto) {
        log.info("Saving new user {} to the database", userRegistrationDto.getEmail());
        if (userRepository.existsByEmail(userRegistrationDto.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        User user = UserMapper.INSTANCE.userRegistrationDtoToUser(userRegistrationDto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName(RoleEnum.ROLE_BRONZE.name()).orElseThrow(RoleNotFoundException::new);
        user.setRoles(Set.of(role));
        user.setCreatedAt(LocalDateTime.now());
        user.setUid(UUID.randomUUID());
        user.setForgotPasswordCode(UUID.randomUUID());
        user.setForgotPasswordCodeExpiresOn(LocalDateTime.now().plusDays(10));
        user.setVerificationCode(UUID.randomUUID());
        user.setVerificationExpiresOn(LocalDateTime.now().plusDays(10));
        user = userRepository.save(user);

        String link = String.format("http://localhost:3000/%s/verify",user.getVerificationCode());
        emailService.sendVerificationEmail(user.getEmail(), EmailUtil.verifyEmail(user.getEmail(), link));
        return user;
    }

    @Override
    public void addRoleToUser(String email, String rolename) {
        log.info("Adding role {} to user {}", rolename, email);
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        Role role = roleRepository.findByName(rolename).orElseThrow(RoleNotFoundException::new);
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepository.findByEmail(username).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserVerificationDTO verifyUser(UserVerificationDTO userVerificationDto) {
        User user = userRepository.findByVerificationCode(UUID.fromString(userVerificationDto.getVerificationCode())).orElseThrow(() -> new UserNotFoundException());

        if(user.isEnabled()){
            throw new UserAlreadyVerifiedException();
        }

        if (LocalDateTime.now().isAfter(user.getVerificationExpiresOn())){
            throw new UserVerificationExpiredException();
        }
        user.setEnabled(true);
        UUID newUid = UUID.randomUUID();
        user.setVerificationCode(newUid);
        user.setVerificationExpiresOn(LocalDateTime.now());

        userRepository.save(user);
        return new UserVerificationDTO(user.getVerificationCode().toString(), user.getEmail());
    }

    @Override
    public void requestVerificationEmail(UserVerificationEmailDTO userVerificationEmailDto) {
        User user = userRepository.findByEmail(userVerificationEmailDto.getEmail()).orElseThrow(UserNotFoundException::new);
        if(user.isEnabled()) {
            throw new UserAlreadyVerifiedException();
        }
        UUID newUid = UUID.randomUUID();
        user.setVerificationCode(newUid);
        user.setVerificationExpiresOn(LocalDateTime.now().plusDays(10));
        userRepository.save(user);

        String link = String.format("http://localhost:3000/%s/verify", newUid);
        emailService.sendVerificationEmail(user.getEmail(), EmailUtil.verifyEmail(user.getEmail(), link));
    }

    @Override
    public void requestForgotPasswordEmail(UserForgotPasswordEmailDTO userForgotPasswordEmailDto) {
        User user = userRepository.findByEmail(userForgotPasswordEmailDto.getEmail()).orElseThrow(UserNotFoundException::new);
        UUID newUid = UUID.randomUUID();
        user.setForgotPasswordCode(newUid);
        user.setForgotPasswordCodeExpiresOn(LocalDateTime.now().plusDays(10));
        userRepository.save(user);

        String link = String.format("http://localhost:3000/%s/reset-password", newUid);
        emailService.sendForgotPasswordEmail(user.getEmail(), EmailUtil.forgotPasswordEmail(user.getEmail(), link));
    }

    @Override
    public void changeUserPassword(UserForgotPasswordDTO userForgotPasswordDto) {
        User user = userRepository.findByForgotPasswordCode(UUID.fromString(userForgotPasswordDto.getCode())).orElseThrow(UserNotFoundException::new);
        UUID newUid = UUID.randomUUID();
        user.setForgotPasswordCode(newUid);
        user.setForgotPasswordCodeExpiresOn(LocalDateTime.now().plusDays(10));
        user.setPassword(passwordEncoder.encode(userForgotPasswordDto.getPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(UserNotFoundException::new);
        if(!user.isEnabled()){
            throw new UserNotVerifiedException();
        }

        Collection<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public String updateMeasurementSystem(String email, String measurementSystem) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        if(measurementSystem.equals(MeasurementSystemEnum.METRIC_SYSTEM.toString())){
            user.setMeasurementSystem(MeasurementSystemEnum.METRIC_SYSTEM);
        } else if (measurementSystem.equals(MeasurementSystemEnum.IMPERIAL_SYSTEM.toString())) {
            user.setMeasurementSystem(MeasurementSystemEnum.IMPERIAL_SYSTEM);
        }
        else {
            // throw error
        }
        userRepository.save(user);
        return user.getMeasurementSystem().toString();
    }
}
