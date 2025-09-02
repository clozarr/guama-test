package com.guama.purchases.shared.validator;

import com.guama.purchases.shared.validator.annotation.ValidEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class EnumValidator implements ConstraintValidator<ValidEnum, Enum<?>> {
    private Set<String> allowedValues;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClass();
        allowedValues = Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            customizeErrorMessage(context);
            return false;
        }
        return true;
    }

    private void customizeErrorMessage(ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                "Valor inválido. Los valores permitidos son: " + String.join(", ", allowedValues)
        ).addConstraintViolation();
    }

}