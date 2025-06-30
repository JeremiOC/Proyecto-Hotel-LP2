package com.cibertec.hotel.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.hotel.entities.EstadoReserva;
@Repository
public interface EstadoReservaRepository extends JpaRepository<EstadoReserva, Integer>{
	
    Optional<EstadoReserva> findByNombreIgnoreCase(String nombre);
}
