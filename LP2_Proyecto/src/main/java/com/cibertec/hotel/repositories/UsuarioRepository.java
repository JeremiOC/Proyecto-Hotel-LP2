package com.cibertec.hotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.hotel.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

}
