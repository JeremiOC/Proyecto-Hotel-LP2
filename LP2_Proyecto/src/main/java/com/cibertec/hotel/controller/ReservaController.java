package com.cibertec.hotel.controller;

import java.io.OutputStream;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.cibertec.hotel.dto.ReservaDTO;
import com.cibertec.hotel.entities.Reserva;
import com.cibertec.hotel.repositories.ReservaRepository;
import com.cibertec.hotel.services.ClienteService;
import com.cibertec.hotel.services.HabitacionService;
import com.cibertec.hotel.services.ReservaService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

	@Autowired
	private ReservaService reservaService;
	@Autowired
	private HabitacionService habitacionService;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private SpringTemplateEngine templateEngine;

    @GetMapping("/registrar")
    public String mostrarFormularioRegistro(Model model) {
    	ReservaDTO dto = new ReservaDTO();
        dto.setNroReserva(reservaService.generarNroReserva());

        model.addAttribute("reservaDto", dto);
        model.addAttribute("habitacionesDisponibles", habitacionService.listarHabitacionesActivas());
        model.addAttribute("clientes", clienteService.listarClientes());
        return "reserva/RegistrarReserva";
    }
	
    @PostMapping("/registrar")
    public String registrarReserva(@Valid @ModelAttribute("reservaDto") ReservaDTO dto,BindingResult result, RedirectAttributes attr,Model model) {
        if(result.hasErrors()) {
        	model.addAttribute("reservaDto", dto); 
	        model.addAttribute("clientes", clienteService.listarClientes());
	        model.addAttribute("habitacionesDisponibles", habitacionService.listarHabitacionesActivas());
	        return "reserva/RegistrarReserva";
        }
    	
    	try {	
            Reserva reserva = reservaService.registrarReserva(dto);
            attr.addFlashAttribute("mensaje", "Reserva registrada correctamente. Nro: " + reserva.getNroReserva());
        } catch (Exception e) {
            attr.addFlashAttribute("error", "Error al registrar la reserva: " + e.getMessage());
        	model.addAttribute("reservaDto", dto); 
	        model.addAttribute("clientes", clienteService.listarClientes());
	        model.addAttribute("habitacionesDisponibles", habitacionService.listarHabitacionesActivas());
        }
        return "redirect:/reservas/registrar";
    }
    @GetMapping("/listado")
    public String mostrarListadoReservas(Model model) {
        model.addAttribute("reservas", reservaService.listarReservas());
        model.addAttribute("estadoSeleccionado", ""); 
        return "reserva/Listado";
    }
    @GetMapping("/filtrar")
    public String listarReservasPorEstado(@RequestParam("estado")String estado,Model model) {

        if (estado == null || estado.isBlank()) {
            model.addAttribute("reservas", reservaService.listarReservas());
        } else {
        	model.addAttribute("reservas", reservaService.consultarReservaPorEstado(estado));
        }
        model.addAttribute("estadoSeleccionado", estado);
    	return "reserva/Listado";
    }
    @GetMapping("/editar/{id}")
    public String mostrarEditarReserva(@PathVariable("id")int id,Model model,RedirectAttributes attr) {
    	Optional<Reserva> opt = reservaService.buscarReservaPorId(id);
		if(opt.isEmpty()) {
			attr.addFlashAttribute("error","Reserva No encontrado");
			return "redirect:/reservas/listado";
		}
    	Reserva reserva = opt.get();
    	ReservaDTO dto = new ReservaDTO();
    	dto.setFechaInicio(reserva.getFechaInicio());
    	dto.setFechaFin(reserva.getFechaFin());
    	dto.setHabitacionId(reserva.getHabitacion().getId());
    	dto.setEstadoId(reserva.getEstadoReserva().getIdEstado());
    	dto.setNroReserva(reserva.getNroReserva());
    	dto.setClienteId(reserva.getCliente().getId());

    	model.addAttribute("habitacionesDisponibles",habitacionService.listarHabitacionesActivas());
    	model.addAttribute("estados",reservaService.listarEstados());
    	model.addAttribute("clienteNombre", reserva.getCliente().getNombres() + " " + reserva.getCliente().getApellidoPaterno());
        model.addAttribute("clienteDni", reserva.getCliente().getNroDocumento());	
        model.addAttribute("habitacion", reserva.getHabitacion());
    	model.addAttribute("reservaDto",dto);
    	model.addAttribute("id",reserva.getId());
    	System.out.println("Habitación de la reserva: " + reserva.getHabitacion());

    	return "reserva/Editar";
    }
    @PostMapping("/editar/{id}")
    public String editarReserva(@PathVariable("id") int id,
                                @Valid @ModelAttribute("reservaDto") ReservaDTO dto,
                                BindingResult result,
                                Model model,
                                RedirectAttributes attr) {

    	if (result.hasErrors()) {
    	    Optional<Reserva> optReserva = reservaService.buscarReservaPorId(id);
    	    if (optReserva.isEmpty()) {
    	        attr.addFlashAttribute("error", "Reserva no encontrada");
    	        return "redirect:/reservas/listado";
    	    }

    	    Reserva reserva = optReserva.get();

    	    model.addAttribute("habitacionesDisponibles", habitacionService.listarHabitacionesActivas());
    	    model.addAttribute("estados", reservaService.listarEstados());

    	    model.addAttribute("clienteNombre", reserva.getCliente().getNombres() + " " + reserva.getCliente().getApellidoPaterno());
    	    model.addAttribute("clienteDni", reserva.getCliente().getNroDocumento());
    	    model.addAttribute("habitacion", reserva.getHabitacion());
    	    System.out.println("Errores de validación:");
    	    result.getAllErrors().forEach(err -> System.out.println(err.getDefaultMessage()));
    	    model.addAttribute("id", id);
    	    return "reserva/Editar";
    	}

        try {
            reservaService.editarReserva(id, dto);
            attr.addFlashAttribute("mensaje", "Reserva actualizada correctamente.");
        } catch (Exception e) {
            attr.addFlashAttribute("error", "Error al actualizar la reserva: " + e.getMessage());
            return "redirect:/reservas/editar/" + id;
        }

        return "redirect:/reservas/listado";
    }

    
    
    @GetMapping("/pdf/{id}")	
    public void generarPDF(@PathVariable Integer id, HttpServletResponse response) throws Exception {
        Reserva reserva = reservaService.buscarReservaPorId(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        Context context = new Context();
        context.setVariable("reserva", reserva);
        
        ClassPathResource imageFile = new ClassPathResource("static/imgs/horizon_luxe_logo.jpeg");
        byte[] imageBytes = FileCopyUtils.copyToByteArray(imageFile.getInputStream());
        String base64 = Base64.getEncoder().encodeToString(imageBytes);
        context.setVariable("logoBase64", base64);
        
        String html = templateEngine.process("pdf/ReservaPDF", context);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Reserva_" + reserva.getNroReserva() + ".pdf");

        try (OutputStream output = response.getOutputStream()) {
            renderer.createPDF(output); 
            output.flush();
        }
    }

}
