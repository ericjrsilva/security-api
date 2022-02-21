import models.PasswordValidationRule;
import models.PasswordValidationRuleRegex;
import models.exception.ValidationException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class PasswordValidationRuleTest {
    private final List<String> WRONG_PASSWORD_POOL = Arrays.asList("","aa","ab", "AAAbbbCc", "AbTp9!foo", "AbTp9!foA", "AbTp9 fok", "1A2B3c4d#!@$%^&*(}");
    private final List<String> REGEX_VALIDATION_RULES = Arrays.asList("^.{9,}$", "[0-9]{1,}", "[A-Z]{1,}", "[a-z]{1,}", "[!@#$%^&*()-+]{1,}", "^(?!.*(.).*\1).+$");


    @Test
    public void testApplyValidation() {

        try {
            for (String regex : REGEX_VALIDATION_RULES) {

                PasswordValidationRule rule = new PasswordValidationRuleRegex(regex);
                rule.apply("AbTp9!fok");
                rule.apply("1A2b3C4d#");
            }

            Assert.assertTrue(true);

        } catch (ValidationException e) {
            e.printStackTrace();
            Assert.fail("Should not throw an exception");
        }

        String currentPassword = null;
        try {
            for (String regex : REGEX_VALIDATION_RULES) {

                PasswordValidationRule rule = new PasswordValidationRuleRegex(regex);
                for (String password : WRONG_PASSWORD_POOL) {
                    currentPassword = password;
                    rule.apply(password);

                }
            }

            Assert.fail("Should throw an exception");

        } catch (ValidationException e) {
            Assert.assertEquals("Password " + currentPassword+ " is invalid", e.getMessage());
        }
    }

    @Test
    public void testApplyValidationInvalid() {

        try {
            new PasswordValidationRuleRegex(null);
            Assert.fail("Should throw an exception");
        } catch (Exception e) {
            Assert.assertEquals("Regex is required", e.getMessage());
        }

        try {
            new PasswordValidationRuleRegex(" ");
            Assert.fail("Should throw an exception");
        } catch (Exception e) {
            Assert.assertEquals("Regex is required", e.getMessage());
        }

        try {
            PasswordValidationRule rule = new PasswordValidationRuleRegex("\\S");
            rule.apply(null);
            Assert.fail("Should throw an exception");
        } catch (Exception e) {
            Assert.assertEquals("Password is required", e.getMessage());
        }
    }
}
