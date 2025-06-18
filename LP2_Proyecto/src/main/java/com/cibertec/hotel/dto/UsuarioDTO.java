	package com.cibertec.hotel.dto;

	import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
	@Getter
	@Setter
	public class UsuarioDTO {

	    @NotBlank(message = "Nombre De Usuario no puede estar vacio")
	    @Size(min = 3, max = 70, message = "El Nombre de usuario debe de estar en el rango de 3 y 70 caracteres")
	    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "El usuario solo puede contener letras, números, puntos, guiones y guiones bajos")
	    private String username;

	    @NotBlank(message = "El correo no puede estar vacío")
	    @Email(message = "Debe ingresar un correo válido")
	    private String correo;

	    @NotBlank(message = "La contraseña no puede estar vacía")
	    @Size(min = 4, message = "Mínimo 4 caracteres")
	    @Pattern( regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).*$",
	              message = "Debe contener al menos una mayúscula, una minúscula y un número")
	    private String clave;

	    @NotNull(message = "Debe seleccionar un rol")
	    private Integer idRol;

	    private MultipartFile imagen;

	}
