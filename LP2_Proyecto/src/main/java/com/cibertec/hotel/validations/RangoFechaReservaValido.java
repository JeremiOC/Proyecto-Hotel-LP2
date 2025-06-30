package com.cibertec.hotel.validations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RangoFechaReservaValidator.class)
public @interface RangoFechaReservaValido {
	String message() default "La fecha de fin debe ser posterior a la fecha de inicio";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
