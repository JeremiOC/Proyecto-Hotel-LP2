package com.cibertec.hotel.services;

import java.util.List;
import java.util.Optional;

import com.cibertec.hotel.dto.ReservaDTO;
import com.cibertec.hotel.entities.Reserva;

public interface ReservaService {
	
	List<Reserva> listarReservas();
	Reserva registrarReserva(ReservaDTO dto);
	Optional<Reserva> editarReserva(int id, ReservaDTO dto);
	Optional<Reserva> buscarReservaPorId(int id);
	List<Reserva> consultarReservaPorEstado(String nombre);
	String generarNroReserva();
}
