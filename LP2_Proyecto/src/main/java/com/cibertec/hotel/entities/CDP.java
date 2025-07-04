package com.cibertec.hotel.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CDP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CDP {

    @Id
    @Column(name  ="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;
    
    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "estado_id")
    private EstadoCDP estado;
    
    
    private BigDecimal total;	
    
    @OneToMany(mappedBy = "cdp",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CDP_Detalle> detalles;
}
