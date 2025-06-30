package com.cibertec.hotel.services;

import java.util.List;
import java.util.Optional;

import com.cibertec.hotel.dto.HabitacionDTO;
import com.cibertec.hotel.entities.Habitacion;
import com.cibertec.hotel.entities.TipoHabitacion;

public interface HabitacionService {
	List<Habitacion>listarHabitaciones();
	List<Habitacion>listarHabitacionesActivas();
	Habitacion registrarHabitacion(HabitacionDTO dto);
	Optional<Habitacion> editarHabitacion(int id , HabitacionDTO dto);
	Optional<Habitacion> encontrarPorId(int id);
	boolean eliminar(int id);
	List<TipoHabitacion> listarTiposHab();
}
