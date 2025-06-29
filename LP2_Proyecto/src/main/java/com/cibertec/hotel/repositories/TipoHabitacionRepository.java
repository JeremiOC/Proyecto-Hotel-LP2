package com.cibertec.hotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.hotel.entities.TipoHabitacion;
@Repository
public interface TipoHabitacionRepository extends JpaRepository<TipoHabitacion, Integer>{

}
