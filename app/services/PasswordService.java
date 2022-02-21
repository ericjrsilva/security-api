package services;

import models.PasswordValidationRule;
import models.data.PasswordData;
import models.PasswordValidationRuleRegex;
import models.exception.ValidationException;
import play.Configuration;
import util.SecurityAPIResponse;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class PasswordService {

    private final String PASSWORD_VALIDATION_RULES = "password.validation.rules";
    private final Configuration configuration;

    @Inject
    public  PasswordService(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * Validates a password based on the rules configured in application conf file.
     * @param passwordData
     * @return
     */
    public SecurityAPIResponse isValid(PasswordData passwordData) {
        SecurityAPIResponse response = new SecurityAPIResponse(SecurityAPIResponse.HTTPStatus.OK);
        try {

            if (passwordData.getPassword() == null) {
                return new SecurityAPIResponse(SecurityAPIResponse.HTTPStatus.BAD_REQUEST, "Password is required.");
            }

            List<PasswordValidationRule> rules = getPasswordValidationRules();
            for (PasswordValidationRule rule : rules) {
                rule.apply(passwordData.getPassword());
            }

            response.setData(Boolean.TRUE);
        } catch (ValidationException e) {
            e.printStackTrace();
            response.setData(Boolean.FALSE);
        } catch (Exception e) {
            e.printStackTrace();
            return new SecurityAPIResponse(SecurityAPIResponse.HTTPStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    /**
     * Gets the password validation rules in application conf file.
     * They are regex configuration to each password rule.
     * @return
     */
    private List<PasswordValidationRule> getPasswordValidationRules() {
        List<Configuration> configRules = configuration.getConfigList(PASSWORD_VALIDATION_RULES);

        if (configRules == null) {
            throw new RuntimeException("Password validation rules should be added in application conf file");
        }

        List<PasswordValidationRule> rules = new ArrayList<>();
        for (Configuration configRule : configRules) {
            for (String key : configRule.keys()) {
                rules.add(new PasswordValidationRuleRegex(configRule.getString(key)));
            }
        }
        return rules;
    }
}
