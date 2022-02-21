package models;

import models.exception.ValidationException;

/**
 * Represents a validation Rule
 */
public interface PasswordValidationRule {
    void apply(String password) throws ValidationException;
}
