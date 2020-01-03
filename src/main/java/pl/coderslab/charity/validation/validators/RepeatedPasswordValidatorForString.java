package pl.coderslab.charity.validation.validators;


import pl.coderslab.charity.validation.constraints.RepeatedPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator logic class of @RepeatedPassword annotation
 * Whole definition of @RepeatedPassword is split between files:
 *  - RepeatedPassword.java - file / Java annotation - define/void annotation
 *  - RepeatedPasswordValidatorForString.java - file / Java class - logic class
 *  - ValidationMessages_pl.properties - that file consist message of that error in Polish (among other error messages)
 *
 */
public class RepeatedPasswordValidatorForString implements ConstraintValidator<RepeatedPassword, String> {

    /**
     * Initialiser of validator
     * @param constraint
     */
    public void initialize(RepeatedPassword constraint) {
    }

    /**
     * Method doing real job of checking data of entity attribute
     * In that case checking if rePassowrd and password are the same values
     * @param rePassword
     * @param password
     * @param context
     * @return
     */
    // TODO: add String password to method parameters   &   cancel String password = rePassword;
    public boolean isValid(String rePassword, ConstraintValidatorContext context) {
        String password = rePassword;
        if (rePassword == null || password == null) {
            return false;
        }
        return rePassword.equals(password);
    }
}
