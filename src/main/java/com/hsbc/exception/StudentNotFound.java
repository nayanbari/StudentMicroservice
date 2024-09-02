package com.hsbc.exception;

public class StudentNotFound extends Exception {
    public StudentNotFound() {
        super();
    }
    public StudentNotFound(String message) {
        super(message);
    }
    public StudentNotFound(String message, Throwable cause) {
        super(message, cause);
    }
    public StudentNotFound(Throwable cause) {
        super(cause);
    }
}
