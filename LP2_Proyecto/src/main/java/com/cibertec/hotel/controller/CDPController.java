package com.cibertec.hotel.controller;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
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
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.cibertec.hotel.dto.CDPDetalleDTO;
import com.cibertec.hotel.dto.CDP_DTO;
import com.cibertec.hotel.entities.CDP;
import com.cibertec.hotel.entities.Reserva;
import com.cibertec.hotel.services.CDPService;
import com.cibertec.hotel.services.ReservaService;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import jakarta.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/comprobantes")
public class CDPController {
	@Autowired
	private CDPService cdpService;
	@Autowired
	private ReservaService reservaService;
	@Autowired 
	private TemplateEngine templateEngine;
	
	
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
	public void registrarCDP(@ModelAttribute CDP_DTO dto, HttpServletResponse response) throws Exception {
	    CDP cdp = cdpService.registrarCDP(dto);
	    Integer idCDP = cdp.getId();

	    Reserva reserva = reservaService.buscarReservaPorId(dto.getIdReserva())
	        .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

	    CDPDetalleDTO detalle = cdpService.generarDetalleDesdeReserva(dto.getIdReserva()).get(0);

	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String usuarioCorreo = auth.getName();

	    Context context = new Context();
	    context.setVariable("reserva", reserva);
	    context.setVariable("detalle", detalle);
	    context.setVariable("usuarioCorreo", usuarioCorreo);
	    context.setVariable("fechaEmision", LocalDate.now());

	    ClassPathResource imgFile = new ClassPathResource("static/imgs/horizon_luxe_logo.jpeg");
	    byte[] imageBytes = imgFile.getInputStream().readAllBytes();
	    String base64Logo = Base64.getEncoder().encodeToString(imageBytes);
	    context.setVariable("logoBase64", base64Logo);

	    String htmlContent = templateEngine.process("pdf/CDP_PDF", context);  

	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    PdfRendererBuilder builder = new PdfRendererBuilder();
	    builder.withHtmlContent(htmlContent, null);
	    builder.toStream(outputStream);
	    builder.run();

	    response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "inline; filename=CDP_" + idCDP + ".pdf");
	    OutputStream responseStream = response.getOutputStream();
	    outputStream.writeTo(responseStream);
	    responseStream.flush();
	    responseStream.close();
	}

	

}
