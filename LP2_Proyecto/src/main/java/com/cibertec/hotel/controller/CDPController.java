package com.cibertec.hotel.controller;

import java.io.OutputStream;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.cibertec.hotel.dto.CDPDetalleDTO;
import com.cibertec.hotel.dto.CDP_DTO;
import com.cibertec.hotel.entities.Reserva;
import com.cibertec.hotel.services.CDPService;
import com.cibertec.hotel.services.ReservaService;


@Controller
@RequestMapping("/comprobantes")
public class CDPController {
	@Autowired
	private CDPService cdpService;
	@Autowired
	private ReservaService reservaService;

	
	
	@GetMapping("/listadoReservas")
	private String listarReservasActivas(Model model) {
		model.addAttribute("listarReservas",cdpService.listarReservasActivas());
		return "cdp/Listado";
	}

	@GetMapping("/ver/{id}")
	public String verCDP(@PathVariable("id") Integer idReserva, Model model) {
	    Reserva reserva = reservaService.buscarReservaPorId(idReserva)
	        .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

	    List<CDPDetalleDTO> detalle = cdpService.generarDetalleDesdeReserva(idReserva);

	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String usuarioCorreo = auth.getName();

	    model.addAttribute("reserva", reserva);
	    model.addAttribute("detalle", detalle.get(0));
	    model.addAttribute("usuarioCorreo", usuarioCorreo);
	    return "cdp/verCDP";
	}
	
	@PostMapping("/registrar")
	public String registrarCDP(@ModelAttribute CDP_DTO dto) {
	    cdpService.registrarCDP(dto);
	    return "redirect:/comprobantes/listadoReservas?success";
	}
	

}
