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
@Table(name = "TipoDocumento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoDocumento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idTipo")
	private Integer idTipoDoc;
	@Column(name = "nombre_doc")
	private String nombre;
}
