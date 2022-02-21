package models.exception;

/**
 * Type of exception specific to validation
 */
public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}
