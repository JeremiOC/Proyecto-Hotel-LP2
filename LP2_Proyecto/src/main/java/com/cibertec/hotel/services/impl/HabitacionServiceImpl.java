package com.cibertec.hotel.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cibertec.hotel.dto.HabitacionDTO;
import com.cibertec.hotel.entities.Habitacion;
import com.cibertec.hotel.entities.TipoHabitacion;
import com.cibertec.hotel.repositories.HabitacionRepository;
import com.cibertec.hotel.repositories.TipoHabitacionRepository;
import com.cibertec.hotel.services.HabitacionService;
@Service
public class HabitacionServiceImpl implements HabitacionService{
	
	private HabitacionRepository habitacionRepository;
	private TipoHabitacionRepository tipoHabitacionRepository;
	
	public HabitacionServiceImpl(HabitacionRepository habitacionRepository,
			TipoHabitacionRepository tipoHabitacionRepository) {
		this.habitacionRepository = habitacionRepository;
		this.tipoHabitacionRepository = tipoHabitacionRepository;
	}

	@Override
	public List<Habitacion> listarHabitaciones() {
		return habitacionRepository.findAll();
	}

	@Override
	public Habitacion registrarHabitacion(HabitacionDTO dto) {
		Habitacion habitacion = new Habitacion();
		habitacion.setNumero(dto.getNumero());
		
		TipoHabitacion tipoHab = tipoHabitacionRepository.findById(dto.getTipoId())
							.orElseThrow(()->new RuntimeException("Tipo de Habitacion no encontrada"));
		habitacion.setTipo(tipoHab);
		habitacion.setDisponible(dto.getDisponible());
		
		return habitacionRepository.save(habitacion);
	}

	@Override
	public Optional<Habitacion> editarHabitacion(int id, HabitacionDTO dto) {
		Habitacion habitacion = habitacionRepository.findById(id)
				.orElseThrow(()-> new IllegalArgumentException("Habitacion con id : "+id+" no encontrada"));
		
		habitacion.setNumero(dto.getNumero());

		TipoHabitacion tipoHab = tipoHabitacionRepository.findById(dto.getTipoId())
				.orElseThrow(()->new RuntimeException("Tipo de Habitacion no encontrada"));
		habitacion.setTipo(tipoHab);
		habitacion.setDisponible(dto.getDisponible());
		
		return Optional.of(habitacionRepository.save(habitacion));
	}

	@Override
	public Optional<Habitacion> encontrarPorId(int id) {
		// TODO Auto-generated method stub
		return habitacionRepository.findById(id);
	}

	@Override
	public boolean eliminar(int id) {
		if (!habitacionRepository.existsById(id)) {
	        return false;
	    }

	    habitacionRepository.deleteById(id);
	    return true;
	}

	@Override
	public List<TipoHabitacion> listarTiposHab() {
		return tipoHabitacionRepository.findAll();
	}

}
