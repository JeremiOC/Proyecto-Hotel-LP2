	package com.cibertec.hotel.dto;

	import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
	@Getter
	@Setter
	public class UsuarioDTO {

	    @NotBlank(message = "Nombre De Usuario no puede estar vacio")
	    @Size(min = 3, max = 70, message = "Username rango invalido de caracteres")
	    private String username;

	    @NotBlank(message = "El correo no puede estar vacío")
	    @Email(message = "Debe ingresar un correo válido")
	    private String correo;

	    @NotBlank(message = "La contraseña no puede estar vacía")
	    @Size(min = 3, message = "Mínimo 3 caracteres")
	    private String clave;

	    @NotNull(message = "Debe seleccionar un rol")
	    private Integer idRol;

	    private MultipartFile imagen;

	}
