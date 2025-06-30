package com.cibertec.hotel.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = NumeroHabitacionUnicoValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NumeroUnico {
	  String message() default "El número de habitación ya existe";
	    Class<?>[] groups() default {};
	    Class<? extends Payload>[] payload() default {};
}
