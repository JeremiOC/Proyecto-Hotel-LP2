package com.cibertec.hotel.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer idCliente;

    @Column(name = "nombre", nullable = false)
    private String nombreCli;

    @Column(name = "apellidos", nullable = false)
    private String apellidosCli;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "documento_identidad", unique = true, nullable = false)
    private String docIdentidad;

    @Column(name = "fecha_nac", nullable = false)
    private LocalDate fechaNac;

}
