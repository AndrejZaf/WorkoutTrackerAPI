package com.example.fitnesstracker.domain.role.exception;

import com.example.fitnesstracker.domain.util.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException() {
        super(ErrorCodes.ROLE_NOT_FOUND);
    }
}
