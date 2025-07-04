package com.cibertec.hotel.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.hotel.entities.EstadoCDP;

public interface EstadoCDPRepository extends JpaRepository<EstadoCDP, Integer> {
	Optional<EstadoCDP> findByNombre(String nombre);
}
