package com.cibertec.hotel.services;

import java.util.List;

import com.cibertec.hotel.dto.CDPDetalleDTO;
import com.cibertec.hotel.dto.CDP_DTO;
import com.cibertec.hotel.entities.CDP;
import com.cibertec.hotel.entities.Reserva;

public interface CDPService{
	
	 CDP registrarCDP(CDP_DTO dto);
	 List<CDPDetalleDTO> generarDetalleDesdeReserva(Integer reservaId);
	 List<Reserva> listarReservasActivas();
}
