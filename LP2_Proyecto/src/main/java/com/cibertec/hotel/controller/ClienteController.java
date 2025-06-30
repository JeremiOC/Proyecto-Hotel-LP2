package com.cibertec.hotel.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.hotel.dto.ClienteDTO;
import com.cibertec.hotel.entities.Cliente;
import com.cibertec.hotel.services.ClienteService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/clientes")
public class ClienteController {
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/mantenimiento")
	public String mantenimiento(Model model) {
	    if (!model.containsAttribute("clienteDto")) {
	        model.addAttribute("clienteDto", new ClienteDTO()); 
	    }
	    model.addAttribute("clientes", clienteService.listarClientes());
	    model.addAttribute("listaTipos", clienteService.listarTiposDoc());
	    return "cliente/GestionCliente";
	}

	@PostMapping("/registrar")
	public String registrarCliente(
		    @Valid @ModelAttribute("clienteDto") ClienteDTO dto,
		    BindingResult result,
		    Model model,
		    RedirectAttributes redirectAttributes){

	    if (result.hasErrors()) {
	        model.addAttribute("clienteDto", dto); // IMPORTANTE
	        model.addAttribute("clientes", clienteService.listarClientes());
	        model.addAttribute("listaTipos", clienteService.listarTiposDoc());
	        return "cliente/GestionCliente";
	    }

	    try {
	        Cliente cliente = clienteService.registrarCliente(dto);
	        redirectAttributes.addFlashAttribute("mensaje", "Cliente creado correctamente: " + cliente.getNombres());
	        return "redirect:/clientes/mantenimiento";
	    } catch (Exception e) {
	        model.addAttribute("error", e.getMessage());
	        model.addAttribute("clienteDto", dto);
	        model.addAttribute("clientes", clienteService.listarClientes());
	        model.addAttribute("listaTipos", clienteService.listarTiposDoc());
	        return "cliente/GestionCliente";
	    }
	}
	
	@GetMapping("/editar/{id}")
	public String mostrarEditarCliente(@PathVariable("id")int id,Model model,
			RedirectAttributes redirectAttributes) {
		Optional<Cliente> opt = clienteService.encontrarPorId(id);
		if(opt.isEmpty()) {
			redirectAttributes.addFlashAttribute("error","Cliente No encontrado");
			return "redirect:/clientes/mantenimiento";
		}
		Cliente cliente = opt.get();
		ClienteDTO dto = new ClienteDTO();
		
		dto.setNombres(cliente.getNombres());
		dto.setApellidoPaterno(cliente.getApellidoPaterno());
		dto.setApellidoMaterno(cliente.getApellidoMaterno());
		dto.setTelefono(cliente.getTelefono());
		dto.setNroDocumento(cliente.getNroDocumento());
		dto.setTipoDocumentoId(cliente.getTipoDocumento().getIdTipoDoc());
		dto.setEmail(cliente.getEmail());
		dto.setFechaNacimiento(cliente.getFechaNacimiento());
		
		model.addAttribute("listaTipos",clienteService.listarTiposDoc());
		model.addAttribute("clienteDTO",dto);
		model.addAttribute("id",cliente.getId());
		
		return "cliente/editar";
	}
	
	@PostMapping("/editar") 
	public String editarCliente(@Valid @ModelAttribute("clienteDTO") ClienteDTO dto,BindingResult result,@RequestParam("id")int id,
			Model model,RedirectAttributes redirectAttributes) {
		if(result.hasErrors()) {
			model.addAttribute("id",id);
			return "cliente/editar";
		}
		try {
			clienteService.editarCliente(id, dto);
			redirectAttributes.addFlashAttribute("mensaje","Cliente Editado correctamente");
		}catch (Exception e) {
			// TODO: handle exception
			redirectAttributes.addFlashAttribute("error","Error al editar al cliente : "+e.getMessage());
		}
		return "redirect:/clientes/mantenimiento";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable("id") int id,RedirectAttributes redirectAttributes) {
		try {
			clienteService.eliminar(id);
			redirectAttributes.addFlashAttribute("mensaje","Cliente con id : "+id+" Eliminado Correctamente");
		}catch (Exception e) {
			// TODO: handle exception
			redirectAttributes.addFlashAttribute("error", "error al eliminar al cliente"+e.getMessage());
		}
		return "redirect:/clientes/mantenimiento";
	}
}
	
	

