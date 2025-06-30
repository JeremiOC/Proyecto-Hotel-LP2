package com.cibertec.hotel.validations;

import com.cibertec.hotel.dto.ReservaDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RangoFechaReservaValidator implements ConstraintValidator<RangoFechaReservaValido, ReservaDTO> {

	@Override
	public boolean isValid(ReservaDTO dto, ConstraintValidatorContext context) {
		 if (dto.getFechaInicio() == null || dto.getFechaFin() == null) {
	            return true; // ya lo valida @NotNull
	        }

	        return dto.getFechaFin().isAfter(dto.getFechaInicio());
	}

}	
