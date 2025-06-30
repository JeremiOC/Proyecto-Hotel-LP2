package com.cibertec.hotel.validations;

import org.springframework.beans.factory.annotation.Autowired;

import com.cibertec.hotel.repositories.HabitacionRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NumeroHabitacionUnicoValidator implements ConstraintValidator<NumeroUnico, String>{

    @Autowired
    private HabitacionRepository habitacionRepository;
	@Override
	public boolean isValid(String numero, ConstraintValidatorContext context) {
		   // Evita errores si el número es null o vacío
        if (numero == null || numero.trim().isEmpty()) {
            return true;
        }

        return !habitacionRepository.existsByNumero(numero);
	}

}
