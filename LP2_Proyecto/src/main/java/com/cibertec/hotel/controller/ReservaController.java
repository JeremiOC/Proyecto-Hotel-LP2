package com.cibertec.hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.hotel.dto.ReservaDTO;
import com.cibertec.hotel.entities.Reserva;
import com.cibertec.hotel.services.ClienteService;
import com.cibertec.hotel.services.HabitacionService;
import com.cibertec.hotel.services.ReservaService;

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
	
    @GetMapping("/registrar")
    public String mostrarFormularioRegistro(Model model) {
    	ReservaDTO dto = new ReservaDTO();
        dto.setNroReserva(reservaService.generarNroReserva()); // Generar antes del submit

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
	
}
