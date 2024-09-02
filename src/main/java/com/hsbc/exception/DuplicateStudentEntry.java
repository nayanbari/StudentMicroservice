package com.hsbc.exception;

public class DuplicateStudentEntry extends Exception {
    public DuplicateStudentEntry() {
        super();
    }
    public DuplicateStudentEntry(String message) {
        super(message);
    }
    public DuplicateStudentEntry(String message, Throwable cause) {
        super(message, cause);
    }
    public DuplicateStudentEntry(Throwable cause) {
        super(cause);
    }
}
