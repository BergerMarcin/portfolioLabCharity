package pl.coderslab.charity.validation.constraints;

import pl.coderslab.charity.validation.validators.RepeatedPasswordValidatorForString;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @RepeatedPassword - own annotation which is define here with stated message name
 * Whole definition of @RepeatedPassword is split between files:
 *  - RepeatedPassword.java - file / Java annotation - define/void annotation
 *  - RepeatedPasswordValidatorForString.java - file / Java class - logic class
 *  - ValidationMessages_pl.properties - that file consist message of that error in Polish (among other error messages)
 *
 */
// @Target from package: java.lang.annotation.Target
@Constraint(validatedBy = RepeatedPasswordValidatorForString.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RUNTIME)
public @interface RepeatedPassword {
    // below there is only name of the message
    // (Polish version of that message text/value is in file ValidationMessages_pl.properties under that message name)
    String message() default "{pl.coderslab.charity.validation.validators.constraints.RepeatedPassword.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
