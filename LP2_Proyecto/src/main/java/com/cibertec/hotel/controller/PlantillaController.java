package com.cibertec.hotel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PlantillaController {

	@GetMapping("/plantilla/cuenta")
	public String mostrarPlantillaCorreo(@RequestParam("correo")String correo,
										@RequestParam("clave")String clave,
										Model model) {
		model.addAttribute("correo",correo);
		model.addAttribute("clave",clave);
		
		return "plantilla/cuenta";
	}
}
