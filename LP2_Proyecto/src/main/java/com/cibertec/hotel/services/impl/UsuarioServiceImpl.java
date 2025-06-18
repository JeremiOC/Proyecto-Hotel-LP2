package com.cibertec.hotel.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.cibertec.hotel.entities.Usuario;
import com.cibertec.hotel.repositories.UsuarioRepository;
import com.cibertec.hotel.services.UsuarioService;

public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	private UsuarioRepository userRepository;
	@Override
	public List<Usuario> listarUsuarios() {

		return userRepository.findAll();
	}

	@Override
	public Usuario registrarUsuario(Usuario usuario) {

		return null;
	}

	@Override
	public Optional<Usuario> actualizar(Usuario usuario) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<Usuario> encontrarPorId(int id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<Usuario> eliminar(int id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
