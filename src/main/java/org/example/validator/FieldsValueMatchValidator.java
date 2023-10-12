package org.example.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.annotation.FieldsValueMatch;
import org.springframework.beans.BeanWrapperImpl;

public class FieldsValueMatchValidator implements ConstraintValidator<FieldsValueMatch, Object> {
    private String field;
    private String fieldMatch;
    private String message;

    public void initialize(FieldsValueMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
        this.message = constraintAnnotation.message();
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {

        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);

        boolean valid;

        if (fieldValue != null) {
            valid = fieldValue.equals(fieldMatchValue);
        } else {
            valid = fieldMatchValue == null;
        }

        if (!valid){
            context.buildConstraintViolationWithTemplate(this.message) // setting the custom message
                    .addPropertyNode(this.fieldMatch) // setting property path
                    .addConstraintViolation() // creating the new constraint violation
                    .disableDefaultConstraintViolation() // disabling the default constraint violation
            ;
        }

        return valid;
    }
}
