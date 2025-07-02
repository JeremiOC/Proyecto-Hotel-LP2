package com.cibertec.hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.cibertec.hotel.dto.UsuarioDTO;
import com.cibertec.hotel.repositories.RolRepository;
import com.cibertec.hotel.services.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RegistroController {
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private RolRepository rolRepository;

	
	@GetMapping("/registro")
	public String mostrarFormRegistro(Model model) {
		
		model.addAttribute("usuarioDTO", new UsuarioDTO());
		model.addAttribute("listaRoles", rolRepository.findAll());
		 
		return "nuevo/registro";
	}
	@PostMapping("/registro")
	public String registrarUsuario(@Valid @ModelAttribute("usuarioDTO") UsuarioDTO dto,BindingResult result,Model model) {
		
		if(result.hasErrors()) {
			model.addAttribute("listaRoles",rolRepository.findAll());
			return "nuevo/registro";
		}
		
       try {
    	   String plantilla = "http://localhost:8080/plantilla/cuenta?correo=[correo]&clave=[clave]";
   			
    	   usuarioService.registrarUsuario(dto, plantilla); 
   			
   		return "redirect:/login?registroExitoso";
       }catch (Exception e) {
    	   	model.addAttribute("error", e.getMessage());
	        model.addAttribute("usuarioDTO", dto);
	        model.addAttribute("listaRoles", rolRepository.findAll());
	        return "nuevo/registro";	
	   }
	}
	
}
