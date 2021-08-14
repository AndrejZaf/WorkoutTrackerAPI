package com.example.fitnesstracker.domain.exercise.exception;

import com.example.fitnesstracker.domain.util.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ExerciseNotFoundException extends RuntimeException {
    public ExerciseNotFoundException() {
        super(ErrorCodes.EXERCISE_NOT_FOUND);
    }
}
