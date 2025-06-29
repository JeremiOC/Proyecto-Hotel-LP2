package com.cibertec.hotel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HabitacionDTO {


    @NotBlank(message = "El número de habitación es obligatorio")
    @Size(min = 3, max = 50, message = "Ingrese 4 valores como minimo")
    private String numero;

    @NotNull(message = "Debe seleccionar un tipo de habitación")
    private Integer tipoId;

    private Boolean disponible = true;
}  