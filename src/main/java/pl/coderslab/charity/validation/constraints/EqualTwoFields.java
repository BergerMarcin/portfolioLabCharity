package pl.coderslab.charity.validation.constraints;

import pl.coderslab.charity.validation.validators.EqualFieldsValidatorForTwoObjects;

import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Generic annotation of checking 2 fields (acc. annotation parameters: baseField, matchField)
 * and reporting to field reportOn (acc. annotation parameter: reportOn)
 * Message is set @ implementation of the annotation
 * Design acc.:
 * http://dolszewski.com/java/multiple-field-validation/
 * chapter 6.2.1 of
 * https://docs.jboss.org/hibernate/validator/5.0/reference/en-US/html/validator-customconstraints.html#section-custom-property-paths
 */
@Constraint(validatedBy = {EqualFieldsValidatorForTwoObjects.class})
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EqualTwoFields {
    // message should be define @ implementation @ method isValid (so below it is empty)
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String baseField();
    String matchField();
    String reportOn();
}
