package com.cibertec.hotel.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cibertec.hotel.dto.HabitacionDTO;
import com.cibertec.hotel.entities.Habitacion;
import com.cibertec.hotel.services.HabitacionService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/habitaciones")
public class HabitacionController {

    @Autowired
    private HabitacionService habitacionService;

    @GetMapping("/mantenimiento")
    public String mantenimiento(Model model) {
        if (!model.containsAttribute("habitacionDto")) {
            model.addAttribute("habitacionDto", new HabitacionDTO());
        }
        model.addAttribute("habitaciones", habitacionService.listarHabitaciones());
        model.addAttribute("tiposHabitacion", habitacionService.listarTiposHab());
        return "habitacion/GestionHabitacion";
    }

    @PostMapping("/registrar")
    public String registrarHabitacion(
            @Valid @ModelAttribute("habitacionDto") HabitacionDTO dto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("habitaciones", habitacionService.listarHabitaciones());
            model.addAttribute("tiposHabitacion", habitacionService.listarTiposHab());
            return "habitacion/GestionHabitacion";
        }

        try {
            habitacionService.registrarHabitacion(dto);
            redirectAttributes.addFlashAttribute("mensaje", "Habitación registrada correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar: " + e.getMessage());
        }

        return "redirect:/habitaciones/mantenimiento";
    }

    @GetMapping("/editar/{id}")
    public String mostrarEditar(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Habitacion> opt = habitacionService.encontrarPorId(id);
        if (opt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Habitación no encontrada");
            return "redirect:/habitaciones/mantenimiento";
        }

        Habitacion hab = opt.get();
        HabitacionDTO dto = new HabitacionDTO();
        dto.setNumero(hab.getNumero());
        dto.setTipoId(hab.getTipo().getId());
        dto.setDisponible(hab.getDisponible());

        model.addAttribute("habitacionDTO", dto);
        model.addAttribute("id", hab.getId());
        model.addAttribute("tiposHabitacion", habitacionService.listarTiposHab());
        return "habitacion/editar";
    }

    @PostMapping("/editar")
    public String editarHabitacion(
            @Valid @ModelAttribute("habitacionDTO") HabitacionDTO dto,
            BindingResult result,
            @RequestParam("id") int id,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("id", id);
            model.addAttribute("tiposHabitacion", habitacionService.listarTiposHab());
            return "habitacion/editar";
        }

        try {
            habitacionService.editarHabitacion(id, dto);
            redirectAttributes.addFlashAttribute("mensaje", "Habitación editada correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al editar: " + e.getMessage());
        }

        return "redirect:/habitaciones/mantenimiento";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        if (habitacionService.eliminar(id)) {
            redirectAttributes.addFlashAttribute("mensaje", "Habitación eliminada correctamente");
        } else {
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar la habitación");
        }
        return "redirect:/habitaciones/mantenimiento";
    }
}
