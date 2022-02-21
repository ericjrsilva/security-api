package models;

import models.exception.ValidationException;

import java.util.regex.Pattern;

/**
 * Stores a regex rule to apply in a password.
 */
public class PasswordValidationRuleRegex implements PasswordValidationRule {
    private Pattern pattern;

    private PasswordValidationRuleRegex() {}

    public PasswordValidationRuleRegex(String regex) {
        if (regex == null || regex.trim().isEmpty()) {
            throw new RuntimeException("Regex is required");
        }
        this.pattern = Pattern.compile(regex);
    }

    /**
     * Validates a password based on a regex configuration.
     * @param password
     */
    @Override
    public void apply(String password) throws ValidationException {
        if (password == null) {
            throw new RuntimeException("Password is required");
        }
        if (!pattern.matcher(password).find()) {
            throw new ValidationException("Password "+ password + " is invalid");
        }
    }
}
