package com.example.fitnesstracker.domain.user.exception;

import com.example.fitnesstracker.domain.util.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserAlreadyVerifiedException extends RuntimeException{
    public UserAlreadyVerifiedException(){
        super(ErrorCodes.USER_ALREADY_VERIFIED);
    }
}
