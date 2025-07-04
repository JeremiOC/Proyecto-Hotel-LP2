package com.cibertec.hotel.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "EstadoReserva")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstadoReserva {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer idEstado;
	
	@Column(name = "nombre")
	private String nombre;

	public EstadoReserva(Integer idEstado) {
		this.idEstado = idEstado;
	}
	
}
