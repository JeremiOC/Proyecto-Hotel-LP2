package com.cibertec.hotel.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.hotel.entities.Habitacion;
@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Integer>{
	List<Habitacion> findByDisponibleTrue();
	boolean existsByNumero(String numero);
}
