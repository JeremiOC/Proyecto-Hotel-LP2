package com.cibertec.hotel.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.cibertec.hotel.dto.UsuarioDTO;
import com.cibertec.hotel.entities.Usuario;

public interface UsuarioService {

	List<Usuario> listarUsuarios();
    Optional<Usuario> encontrarPorId(int id);
    Usuario registrarUsuario(UsuarioDTO dto, String urlPlantillaCorreo) throws IOException;
    Optional<Usuario> actualizarUsuario(int id, UsuarioDTO dto) throws IOException;
    Optional<Boolean> eliminarUsuario(int id);

}
