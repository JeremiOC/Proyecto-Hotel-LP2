package com.cibertec.hotel.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDTO {

    @NotBlank(message = "Nombres no puede estar vacío")
    @Size(min = 3, max = 50, message = "Numero de caracteres invalidos")
    private String nombres;

    @NotBlank(message = "Apellido paterno no puede estar vacío")
    @Size(min = 3, max = 50, message = "Numero de caracteres invalidos")
    private String apellidoPaterno;

    @NotBlank(message = "Apellido materno no puede estar vacío")
    @Size(min = 3, max = 50, message = "Numero de caracteres invalidos")
    private String apellidoMaterno;

    @Size(min = 9, max = 12, message = "El número de teléfono debe tener entre 9 y 12 caracteres")
    private String telefono;

    @Email(message = "El email no es válido")
    private String email;

    @NotBlank(message = "Documento de identidad del cliente no puede estar vacío")
    @Size(min = 8, max = 12, message = "El documento de identidad debe tener entre 8 y 12 caracteres")
    private String nroDocumento;

    @NotNull(message = "Debe seleccionar un tipo de documento")
    private Integer tipoDocumentoId;

    @NotNull(message = "Fecha de nacimiento no puede estar vacía")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private LocalDate fechaNacimiento;

    // Este campo es opcional, según el uso (listar/editar)
    private Boolean activo;
}
