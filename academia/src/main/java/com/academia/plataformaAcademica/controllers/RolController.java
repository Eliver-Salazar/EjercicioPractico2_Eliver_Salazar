/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.academia.plataformaAcademica.controllers;

/**
 *
 * @author Eliver Salazar Campo
 */

import com.academia.plataformaAcademica.domain.RolSistema;
import com.academia.plataformaAcademica.service.RolService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/roles")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("roles", rolService.listarTodos());
        return "rol/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("rol", new RolSistema());
        return "rol/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("rol") RolSistema rol,
                          BindingResult result,
                          RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "rol/formulario";
        }
        rolService.guardar(rol);
        redirectAttributes.addFlashAttribute("mensaje", "Rol guardado correctamente");
        return "redirect:/admin/roles";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        RolSistema rol = rolService.obtenerPorId(id);
        if (rol == null) {
            redirectAttributes.addFlashAttribute("error", "Rol no encontrado");
            return "redirect:/admin/roles";
        }
        model.addAttribute("rol", rol);
        return "rol/formulario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        rolService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensaje", "Rol eliminado");
        return "redirect:/admin/roles";
    }
}

