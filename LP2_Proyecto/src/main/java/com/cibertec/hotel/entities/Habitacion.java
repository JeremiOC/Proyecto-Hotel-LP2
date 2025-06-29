package com.cibertec.hotel.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Habitacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Habitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_id", nullable = false)
    private TipoHabitacion tipo;

    @Column(nullable = false)
    private Boolean disponible = true;
}
