package com.cibertec.hotel.dto;

import java.time.LocalDate;

import com.cibertec.hotel.validations.RangoFechaReservaValido;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@RangoFechaReservaValido 
public class ReservaDTO {
    @NotNull(message = "La fecha de inicio es obligatoria")
    @Future(message = "La fecha de inicio debe ser una fecha futura")
    private LocalDate fechaInicio;
    
    private String nroReserva;
    
    @NotNull(message = "La fecha de fin es obligatoria")
    @Future(message = "La fecha de fin debe ser una fecha futura")
    private LocalDate fechaFin;

    @NotNull(message = "Debe seleccionar un cliente")
    private Integer clienteId;


    @NotNull(message = "Debe seleccionar una habitación")
    private Integer habitacionId;

    private Integer estadoId;
}
