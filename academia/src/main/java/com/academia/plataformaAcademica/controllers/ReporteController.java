/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.academia.plataformaAcademica.controllers;

/**
 *
 * @author Eliver Salazar Campo
 */

import com.academia.plataformaAcademica.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profesor")
public class ReporteController {

    private final UsuarioService usuarioService;

    public ReporteController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/reportes")
    public String reportes(Model model) {
        long activos = usuarioService.contarActivos();
        long inactivos = usuarioService.contarInactivos();

        model.addAttribute("activos", activos);
        model.addAttribute("inactivos", inactivos);
        model.addAttribute("usuariosRecientes", usuarioService.listarOrdenadosPorFechaDesc());

        return "profesor/reportes";
    }
}

