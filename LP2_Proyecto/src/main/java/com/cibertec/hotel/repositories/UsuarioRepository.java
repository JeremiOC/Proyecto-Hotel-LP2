package com.cibertec.hotel.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.hotel.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	boolean existsByCorreo(String correo);
	Optional<Usuario> findByCorreo(String correo);
}
