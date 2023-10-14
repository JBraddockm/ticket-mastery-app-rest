package org.example.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.annotation.ValidDueDate;
import org.springframework.beans.BeanWrapperImpl;

import java.time.LocalDate;

public class DueDateConstraintValidator implements ConstraintValidator<ValidDueDate, Object> {

    private String startDate;
    private String endDate;
    private String message;
    @Override
    public void initialize(ValidDueDate constraintAnnotation) {
        this.startDate = constraintAnnotation.startDate();
        this.endDate = constraintAnnotation.endDate();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(startDate);
        Object fieldCheckValue = new BeanWrapperImpl(value).getPropertyValue(endDate);

        boolean valid = false;

        if(fieldValue != null && fieldCheckValue != null){
            LocalDate projectStartDate = LocalDate.parse(fieldValue.toString());
            LocalDate projectEndDate = LocalDate.parse(fieldCheckValue.toString());
            valid = projectEndDate.isAfter(projectStartDate) || projectEndDate.isEqual(projectStartDate);
        }

        if (!valid){
            context.buildConstraintViolationWithTemplate(this.message) // setting the custom message
                    .addPropertyNode(this.endDate) // setting property path
                    .addConstraintViolation() // creating the new constraint violation
                    .disableDefaultConstraintViolation(); // disabling the default constraint violation
        }

        return valid;
    }
}
