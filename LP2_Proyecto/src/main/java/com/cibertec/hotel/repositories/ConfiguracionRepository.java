package com.cibertec.hotel.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.hotel.entities.Configuracion;

@Repository
public interface ConfiguracionRepository extends JpaRepository<Configuracion, Integer>{
		List<Configuracion> findByRecurso(String recurso);
}
