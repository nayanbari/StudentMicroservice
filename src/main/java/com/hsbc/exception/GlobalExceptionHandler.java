package com.hsbc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateStudentEntry.class)
    @ResponseStatus(reason = "Duplicate student entry", code = HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleDuplicateStudentEntry() {
        // log the exception

    }

    @ExceptionHandler(StudentNotFound.class)
    @ResponseStatus(reason = "Student not found", code = HttpStatus.NOT_FOUND)
    public void handleStudentNotFound() {
        // log the exception
        System.out.println("Student not found");
    }
}
