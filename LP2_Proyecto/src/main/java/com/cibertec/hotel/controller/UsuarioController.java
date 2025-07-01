package com.cibertec.hotel.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.cibertec.hotel.dto.UsuarioDTO;
import com.cibertec.hotel.entities.Usuario;
import com.cibertec.hotel.repositories.RolRepository;
import com.cibertec.hotel.services.UsuarioService;

import jakarta.validation.Valid;


import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private RolRepository rolRepository;
	
	@GetMapping("/mantenimiento")
	public String mantenimiento(Model model) {
		
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
	@GetMapping("/editar/{id}")
	public String mostrarEditarUsuario(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
	    Optional<Usuario> usuarioOpt = usuarioService.encontrarPorId(id);
	    if (usuarioOpt.isEmpty()) {
	        redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
	        return "redirect:/usuarios/mantenimiento";
	    }

	    Usuario usuario = usuarioOpt.get();
	    UsuarioDTO dto = new UsuarioDTO();
	    dto.setUsername(usuario.getUsername());
	    dto.setCorreo(usuario.getCorreo());
	    dto.setClave(usuario.getClave()); 
	    dto.setIdRol(usuario.getIdRol().getIdRol());

	    model.addAttribute("usuarioDTO", dto);
	    model.addAttribute("id", usuario.getIdUsuario());
	    model.addAttribute("listaRoles", rolRepository.findAll());

	    return "usuario/editar";
	}
	@PostMapping("/editar")
	public String editarUsuario(
            @Valid @ModelAttribute UsuarioDTO dto,
            BindingResult result,
            @RequestParam("id") int id,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("listaRoles", rolRepository.findAll());
            model.addAttribute("id", id);
            return "usuario/editar";
        }

        try {
            usuarioService.actualizarUsuario(id, dto);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario actualizado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/usuarios/mantenimiento";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarUsuario(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
	    try {
	        usuarioService.eliminarUsuario(id);
	        redirectAttributes.addFlashAttribute("mensaje", "Usuario eliminado correctamente");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "Error al eliminar usuario: " + e.getMessage());
	    }
	    return "redirect:/usuarios/mantenimiento";
	}

}
