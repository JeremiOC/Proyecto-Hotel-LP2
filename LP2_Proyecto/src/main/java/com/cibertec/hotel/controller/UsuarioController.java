package com.cibertec.hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.cibertec.hotel.dto.UsuarioDTO;
import com.cibertec.hotel.entities.Usuario;
import com.cibertec.hotel.repositories.RolRepository;
import com.cibertec.hotel.services.UsuarioService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
	
	@GetMapping("/form")
	public String mostrarFormulario(Model model) {
		
		 model.addAttribute("usuarioDTO", new UsuarioDTO());
		 model.addAttribute("listaRoles", rolRepository.findAll());
		return "usuario/usuarios/form";
	}
	
	@GetMapping("/listaUsuarios")
	public String listaUsuario(Model model) {
	    model.addAttribute("usuarios", usuarioService.listarUsuarios());
	    return "usuario/listar";  // <--- Aquí asegúrate de tener este HTML
	}

	@PostMapping("/registrar")
	public String postMethodName(@ModelAttribute UsuarioDTO usuarioDTO,
			@RequestParam("imagen")MultipartFile imagen,
			RedirectAttributes redirectAttributes) {
		try {
			usuarioDTO.setImagen(imagen);
			String plantilla  = "http://localhost:8080/plantilla/cuenta?correo=[correo]&clave=[clave]";
			Usuario nuevo = usuarioService.registrarUsuario(usuarioDTO, plantilla);
			redirectAttributes.addFlashAttribute("mensaje", "Usuario creado correctamente: " + nuevo.getUsername());
		}catch (Exception e) {
				redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
		        redirectAttributes.addFlashAttribute("usuarioDTO", usuarioDTO);
		}
		return "redirect:/usuarios/form";
	}
	
	
}
