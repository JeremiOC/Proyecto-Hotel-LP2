package com.cibertec.hotel.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer idUsuario;


	@Column(name = "username", unique = true,nullable = false )
	private String username;

	@Column(name = "correo", length = 50, unique = true, nullable = false)
    private String correo;

	@Column(name = "clave", length = 100, nullable = false)
    private String clave;

	@Column(name = "urlFoto", length = 500)
    private String urlFoto;

    @Column(name = "nombreFoto", length = 100)
    private String nombreFoto;

	@ManyToOne
	@JoinColumn(name = "idRol")
	private Rol idRol;

}
