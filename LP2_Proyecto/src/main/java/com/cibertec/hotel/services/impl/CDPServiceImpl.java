package com.cibertec.hotel.services.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cibertec.hotel.dto.CDPDetalleDTO;
import com.cibertec.hotel.dto.CDP_DTO;
import com.cibertec.hotel.entities.CDP;
import com.cibertec.hotel.entities.CDP_Detalle;
import com.cibertec.hotel.entities.EstadoReserva;
import com.cibertec.hotel.entities.Reserva;
import com.cibertec.hotel.entities.TipoHabitacion;
import com.cibertec.hotel.entities.Usuario;
import com.cibertec.hotel.repositories.CDPRepository;
import com.cibertec.hotel.repositories.CDP_DetalleRepository;
import com.cibertec.hotel.repositories.EstadoCDPRepository;
import com.cibertec.hotel.repositories.ReservaRepository;
import com.cibertec.hotel.repositories.UsuarioRepository;
import com.cibertec.hotel.services.CDPService;

@Service
public class CDPServiceImpl implements CDPService {
	
	private ReservaRepository reservaRepository;
	private CDPRepository cdpRepository;
	private EstadoCDPRepository estadoCDPRepository;
	private CDP_DetalleRepository cdpDetalleRepository;
	private UsuarioRepository usuarioRepository;
	
	


	public CDPServiceImpl(ReservaRepository reservaRepository, CDPRepository cdpRepository,
			EstadoCDPRepository estadoCDPRepository, CDP_DetalleRepository cdpDetalleRepository,
			UsuarioRepository usuarioRepository) {
		this.reservaRepository = reservaRepository;
		this.cdpRepository = cdpRepository;
		this.estadoCDPRepository = estadoCDPRepository;
		this.cdpDetalleRepository = cdpDetalleRepository;
		this.usuarioRepository = usuarioRepository;
	}



	
	@Override
	public CDP registrarCDP(CDP_DTO dto) {
		Authentication auth  = SecurityContextHolder.getContext().getAuthentication();
		String usuarioAuth = auth.getName();
		
		Reserva reserva = reservaRepository.findById(dto.getIdReserva())
				.orElseThrow(()->new RuntimeException("Reserva No encontrada"));
		
		Usuario usu = usuarioRepository.findByCorreo(usuarioAuth)
				.orElseThrow(()->new RuntimeException("Usuario No encontrado"));
		
		
		CDP cdp = new CDP();
		
		cdp.setReserva(reserva);
		cdp.setUsuario(usu);
		cdp.setFechaEmision(LocalDate.now());
		cdp.setEstado(estadoCDPRepository.findByNombre("PAGADO").orElseThrow());

		
		CDPDetalleDTO detalleDTO = generarDetalleDesdeReserva(dto.getIdReserva()).get(0);
		
		CDP_Detalle detalle = new CDP_Detalle();
		
		cdp.setTotal(detalleDTO.getTotal());
		cdpRepository.save(cdp);

		 detalle.setCdp(cdp);
		 detalle.setDescripcion(detalleDTO.getDescripcion());
		 detalle.setCantidad(detalleDTO.getCantidad());
		 detalle.setPrecioUnitario(detalleDTO.getPrecioUnitario());
		 detalle.setSubtotal(detalleDTO.getSubtotal());
		 detalle.setIgv(detalleDTO.getIgv());
		 detalle.setTotal(detalleDTO.getTotal());
		 
		 cdpDetalleRepository.save(detalle);
		 
		 reserva.setEstadoReserva(new EstadoReserva(2));
		 reservaRepository.save(reserva);
		 
		 return cdp;
	}




	@Override
	public List<CDPDetalleDTO> generarDetalleDesdeReserva(Integer reservaId) {
		Reserva reserva = reservaRepository.findById(reservaId)
	            .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

	        TipoHabitacion tipo = reserva.getHabitacion().getTipo();
	        long dias = ChronoUnit.DAYS.between(reserva.getFechaInicio(), reserva.getFechaFin());

	        BigDecimal precio = tipo.getPrecio();
	        BigDecimal subtotal = precio.multiply(BigDecimal.valueOf(dias));
	        BigDecimal igv = subtotal.multiply(BigDecimal.valueOf(0.18));
	        BigDecimal total = subtotal.add(igv);

	        CDPDetalleDTO detalle = new CDPDetalleDTO();
	        detalle.setDescripcion("Reserva de habitación " + tipo.getDescripcion());
	        detalle.setCantidad((int) dias);
	        detalle.setPrecioUnitario(precio);
	        detalle.setSubtotal(subtotal);
	        detalle.setIgv(igv);
	        detalle.setTotal(total);

	        return List.of(detalle);
		
		
	}




	@Override
	public List<Reserva> listarReservasActivas() {
		return reservaRepository.findByEstadoReservaNombreIgnoreCase("PENDIENTE");
	}
	
	

}
