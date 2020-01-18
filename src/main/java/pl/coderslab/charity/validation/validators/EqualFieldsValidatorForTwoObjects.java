package pl.coderslab.charity.validation.validators;

import pl.coderslab.charity.validation.constraints.EqualTwoFields;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

/**
 * Generic implementation of annotation of checking 2 fields (acc. annotation parameters: baseField, matchField)
 * and reporting message to field reportOn (acc. annotation parameter: reportOn)
 * Message is set here @ implementation of the annotation, @ method initialize
 * Design acc.:
 * http://dolszewski.com/java/multiple-field-validation/
 * chapter 6.2.1 of
 * https://docs.jboss.org/hibernate/validator/5.0/reference/en-US/html/validator-customconstraints.html#section-custom-property-paths
 */
public class EqualFieldsValidatorForTwoObjects implements ConstraintValidator<EqualTwoFields, Object> {

    private String baseField;
    private String matchField;
    private String reportOn;


    @Override
    public void initialize(EqualTwoFields constraint) {
        baseField = constraint.baseField();
        matchField = constraint.matchField();
        reportOn = constraint.reportOn();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        try {
            Object baseFieldValue = getFieldValue(object, baseField);
            Object matchFieldValue = getFieldValue(object, matchField);
            boolean isValid = (baseFieldValue != null && baseFieldValue.equals(matchFieldValue));
            // Set reporting on field acc. reportedOn
            // based on chapter 6.2.1 from:
            // https://docs.jboss.org/hibernate/validator/5.0/reference/en-US/html/validator-customconstraints.html#section-custom-property-paths
            if ( !isValid ) {
                context.disableDefaultConstraintViolation();
                context
                        .buildConstraintViolationWithTemplate(
                                "{pl.coderslab.charity.validation.validators.constraints.EqualTwoFields.message}")
                        .addPropertyNode(reportOn).addConstraintViolation();
            }
            return isValid;


        } catch (Exception e) {
            // log error
            return false;
        }
    }

    private Object getFieldValue(Object object, String fieldName) throws Exception {
        Class<?> clazz = object.getClass();
        Field passwordField = clazz.getDeclaredField(fieldName);
        passwordField.setAccessible(true);
        return passwordField.get(object);
    }
}