package com.cibertec.hotel.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.hotel.entities.Reserva;
@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
	List<Reserva> findByEstadoReservaNombreIgnoreCase(String nombre);
}
