package pl.coderslab.charity.validation.validators;


import pl.coderslab.charity.services.UserService;
import pl.coderslab.charity.validation.constraints.UniqueEmail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator logic class of @UniqueEmail annotation
 * Whole definition of @UniqueEmail is split between files:
 *  - UniqueEmail.java - file / Java annotation - define/void annotation
 *  - UniqueEmailValidatorForString.java - file / Java class - logic class
 *  - ValidationMessages_pl.properties - that file consist message of that error in Polish (among other error messages)
 */
public class UniqueEmailValidatorForString implements ConstraintValidator<UniqueEmail, String> {

    UserService userService;

    public UniqueEmailValidatorForString(UserService userService) {
        this.userService = userService;
    }

    /**
     * Initialiser of validator
     * @param constraint
     */
    public void initialize(UniqueEmail constraint) {
    }

    /**
     * Method doing real job of checking data of entity/DTO field/attribute
     * In that case checking if email is unique based on emails in DataBase
     * @param email
     * @param context
     * @return
     */
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return false;
        }
        return userService.isEmailUnique(email);
    }
}
