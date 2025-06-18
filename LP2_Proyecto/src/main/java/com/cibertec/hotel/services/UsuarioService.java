package com.cibertec.hotel.services;

import java.util.List;
import java.util.Optional;

import com.cibertec.hotel.entities.Usuario;

public interface UsuarioService {

	List<Usuario> listarUsuarios();
	Usuario registrarUsuario(Usuario usuario);
	Optional<Usuario> actualizar(Usuario usuario);
	Optional<Usuario> encontrarPorId(int id);
	Optional<Usuario> eliminar(int id);

}
