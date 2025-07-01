package com.cibertec.hotel.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cibertec.hotel.dto.ReservaDTO;
import com.cibertec.hotel.entities.Cliente;
import com.cibertec.hotel.entities.EstadoReserva;
import com.cibertec.hotel.entities.Habitacion;
import com.cibertec.hotel.entities.Reserva;
import com.cibertec.hotel.repositories.ClienteRepository;
import com.cibertec.hotel.repositories.EstadoReservaRepository;
import com.cibertec.hotel.repositories.HabitacionRepository;
import com.cibertec.hotel.repositories.ReservaRepository;
import com.cibertec.hotel.services.ReservaService;

@Service
public class ReservaServiceImpl implements ReservaService{
	
	private ReservaRepository reservaRepository;
	private ClienteRepository clienteRepository;
	private HabitacionRepository habitacionRepository;
	private EstadoReservaRepository estadoReservaRepository;
	
	
	
	
	public ReservaServiceImpl(ReservaRepository reservaRepository, ClienteRepository clienteRepository,
			HabitacionRepository habitacionRepository, EstadoReservaRepository estadoReservaRepository) {
		this.reservaRepository = reservaRepository;
		this.clienteRepository = clienteRepository;
		this.habitacionRepository = habitacionRepository;
		this.estadoReservaRepository = estadoReservaRepository;
	}

	@Override
	public List<Reserva> listarReservas() {
		return reservaRepository.findAll();
	}

	@Override
	public Reserva registrarReserva(ReservaDTO dto) {
		Reserva reserva = new Reserva();
		
		String nroReserva = generarNroReserva();
		
	    reserva.setNroReserva(nroReserva);
		reserva.setFechaInicio(dto.getFechaInicio());
		
		reserva.setFechaFin(dto.getFechaFin());
		
		Cliente cliente = clienteRepository.findById(dto.getClienteId())
		        .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
		
		 Habitacion habitacion = habitacionRepository.findById(dto.getHabitacionId())
			        .orElseThrow(() -> new IllegalArgumentException("Habitación no encontrada"));
		 
		  if (!habitacion.getDisponible()) {
		        throw new IllegalStateException("La habitación ya está ocupada");
		    }
		  reserva.setCliente(cliente);
		  reserva.setHabitacion(habitacion);
		  reserva.setEstadoReserva(estadoReservaRepository.findByNombreIgnoreCase("PENDIENTE")
				  				.orElseThrow(()-> new RuntimeException("Estado PENDIENTE no encontrado")));
		  habitacion.setDisponible(false);
		  habitacionRepository.save(habitacion);
		  
	
		return reservaRepository.save(reserva);
	}

	@Override
	public Optional<Reserva> editarReserva(int id, ReservaDTO dto) {
		Reserva reserva = reservaRepository.findById(id)
					.orElseThrow(()->new RuntimeException("Reserva con id : "+id+" no encontrada"));
		reserva.setFechaInicio(dto.getFechaInicio());
		reserva.setFechaFin(dto.getFechaFin());
		Habitacion anterior = reserva.getHabitacion();
		Cliente cliente = clienteRepository.findById(dto.getClienteId())
		        .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
		reserva.setCliente(cliente);
		Habitacion habitacionNueva = habitacionRepository.findById(dto.getHabitacionId())
		        .orElseThrow(() -> new IllegalArgumentException("Habitación no encontrada"));
		if(!anterior.getId().equals(habitacionNueva.getId())) {
			anterior.setDisponible(true);
			habitacionRepository.save(anterior);
			if(!habitacionNueva.getDisponible()) {
				throw new IllegalArgumentException("La nueva habitacion esta ocupada");
			}
			habitacionNueva.setDisponible(false);
			reserva.setHabitacion(habitacionNueva);
			habitacionRepository.save(habitacionNueva);
		}
	    EstadoReserva estado = estadoReservaRepository.findById(dto.getEstadoId())
	            .orElseThrow(() -> new IllegalArgumentException("Estado de reserva no válido"));

	   reserva.setEstadoReserva(estado);
		
		return Optional.of(reservaRepository.save(reserva));
	}

	@Override
	public Optional<Reserva> buscarReservaPorId(int id) {
		return reservaRepository.findById(id);
	}

	@Override
	public List<Reserva> consultarReservaPorEstado(String nombre) {
		return reservaRepository.findByEstadoReservaNombreIgnoreCase(nombre);
	}

	@Override
	public String generarNroReserva() {
		 long total = reservaRepository.count() + 1;
		 return String.format("%06d", total); 
	}

	@Override
	public List<EstadoReserva> listarEstados() {
		return estadoReservaRepository.findAll();
	}

}
