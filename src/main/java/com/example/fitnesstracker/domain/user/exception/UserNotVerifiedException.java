package com.example.fitnesstracker.domain.user.exception;

import com.example.fitnesstracker.domain.util.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserNotVerifiedException extends RuntimeException{
    public UserNotVerifiedException() {
        super(ErrorCodes.USER_NOT_VERIFIED);
    }
}
