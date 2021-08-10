package com.example.fitnesstracker.domain.user.exception;

import com.example.fitnesstracker.domain.util.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super(ErrorCodes.USER_ALREADY_EXISTS);
    }
}
