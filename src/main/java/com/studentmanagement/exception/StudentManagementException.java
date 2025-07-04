package com.studentmanagement.exception;

/**
 * Custom exception class for Student Management System
 */
public class StudentManagementException extends Exception {
    
    public StudentManagementException(String message) {
        super(message);
    }
    
    public StudentManagementException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public StudentManagementException(Throwable cause) {
        super(cause);
    }
}

/**
 * Exception for validation errors
 */
class ValidationException extends StudentManagementException {
    public ValidationException(String message) {
        super(message);
    }
}

/**
 * Exception for database operation errors
 */
class DatabaseException extends StudentManagementException {
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

/**
 * Exception for entity not found errors
 */
class EntityNotFoundException extends StudentManagementException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}