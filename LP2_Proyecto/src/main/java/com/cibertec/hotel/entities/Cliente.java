package com.cibertec.hotel.entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(name = "apell_pat", nullable = false, length = 100)
    private String apellidoPaterno;

    @Column(name = "apell_mat", nullable = false, length = 100)
    private String apellidoMaterno;

    @Column(length = 50)
    private String telefono;

    @Column(length = 50)
    private String email;

    @Column(name = "nro_documento", nullable = false, unique = true, length = 20)
    private String nroDocumento;

    @ManyToOne
    @JoinColumn(name = "tipo_doc")
    private TipoDocumento tipoDocumento;

    @Column(nullable = false)
    private boolean activo = true;
    
    @Column(name = "fecha_nac", nullable = false)
    private LocalDate fechaNacimiento;
}
