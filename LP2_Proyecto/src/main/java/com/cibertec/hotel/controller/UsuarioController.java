package com.cibertec.hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.cibertec.hotel.dto.UsuarioDTO;
import com.cibertec.hotel.entities.Usuario;
import com.cibertec.hotel.repositories.RolRepository;
import com.cibertec.hotel.services.UsuarioService;

import jakarta.validation.Valid;


import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private RolRepository rolRepository;
	
	@GetMapping("/mantenimiento")
	public String mostrarFormulario(Model model) {
		
		 model.addAttribute("usuarioDTO", new UsuarioDTO());
		 model.addAttribute("listaRoles", rolRepository.findAll());
		 
		 model.addAttribute("usuarios", usuarioService.listarUsuarios());

		 return "usuario/Gestion";
	}
	
	@PostMapping("/registrar")
	public String registrarUsuario(
	        @Valid @ModelAttribute UsuarioDTO usuarioDTO,
	        BindingResult result,
	        Model model,
	        RedirectAttributes redirectAttributes) {

	    if (result.hasErrors()) {
	        model.addAttribute("listaRoles", rolRepository.findAll());
	        model.addAttribute("usuarios", usuarioService.listarUsuarios());
	        return "usuario/Gestion";
	    }

	    try {
	        String plantilla = "http://localhost:8080/plantilla/cuenta?correo=[correo]&clave=[clave]";
	        Usuario nuevo = usuarioService.registrarUsuario(usuarioDTO, plantilla);

	        redirectAttributes.addFlashAttribute("mensaje", "Usuario creado correctamente: " + nuevo.getUsername());
	        return "redirect:/usuarios/mantenimiento";

	    } catch (Exception e) {
	        model.addAttribute("error", e.getMessage());
	        model.addAttribute("usuarioDTO", usuarioDTO);
	        model.addAttribute("listaRoles", rolRepository.findAll());
	        model.addAttribute("usuarios", usuarioService.listarUsuarios());
	        return "usuario/Gestion";
	    }
	}

	
}
