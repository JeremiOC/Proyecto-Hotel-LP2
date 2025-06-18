package com.cibertec.hotel.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class ClienteDTO {

	@NotBlank(message = "Nombre de cliente no puede estar vacio")
	private String nombreCli;

	@NotBlank(message = "Apellidos de cliente no puede estar vacio")
	private String apellidosCli;

	@Size(min = 9, max = 12, message =  "El NUMERO DE TELEFONO debe de tener entre 9 y 12 caracteres")
	private String telefono;

	@Size(min = 8, max = 12, message =  "El doc de identidad debe de tener entre 8 y 12 caracteres")
	@NotBlank(message = "Documento de identidad del cliente no puede estar vacio")
	private String docIdentidad;

	@NotNull(message = "Fecha de Nacimiento no puede estar vacia")
	@Past(message = "La fecha de nacimiento debe ser una fecha distinta a la de hoy")
	private LocalDate fechaNac;
}
