package pl.coderslab.charity.validation.constraints;

import pl.coderslab.charity.validation.validators.UniqueEmailValidatorForString;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validator logic class of @UniqueEmail annotation
 * Whole definition of @UniqueEmail is split between files:
 *  - UniqueEmail.java - file / Java annotation - define/void annotation
 *  - UniqueEmailValidatorForString.java - file / Java class - logic class
 *  - ValidationMessages_pl.properties - that file consist message of that error in Polish (among other error messages)
 */
// @Target from package: java.lang.annotation.Target
@Constraint(validatedBy = UniqueEmailValidatorForString.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RUNTIME)
public @interface UniqueEmail {
    // below there is only name of the message
    // (Polish version of that message text/value is in file ValidationMessages_pl.properties under that message name)
    String message() default "{pl.coderslab.charity.validation.validators.constraints.UniqueEmail.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
