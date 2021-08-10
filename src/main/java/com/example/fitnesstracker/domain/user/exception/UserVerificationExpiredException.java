package com.example.fitnesstracker.domain.user.exception;

import com.example.fitnesstracker.domain.util.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserVerificationExpiredException extends RuntimeException {
    public UserVerificationExpiredException() {
        super(ErrorCodes.USER_VERIFICATION_EXPIRED);
    }
}
