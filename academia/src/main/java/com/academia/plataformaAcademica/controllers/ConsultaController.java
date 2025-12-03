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
import com.academia.plataformaAcademica.domain.UsuarioPlataforma;
import com.academia.plataformaAcademica.service.RolService;
import com.academia.plataformaAcademica.service.UsuarioService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin/consultas")
public class ConsultaController {

    private final UsuarioService usuarioService;
    private final RolService rolService;

    public ConsultaController(UsuarioService usuarioService, RolService rolService) {
        this.usuarioService = usuarioService;
        this.rolService = rolService;
    }

    @GetMapping
    public String consultas(
            @RequestParam(required = false) Long rolId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta,
            @RequestParam(required = false) String texto,
            Model model) {

        List<UsuarioPlataforma> porRol = Collections.emptyList();
        List<UsuarioPlataforma> porFechas = Collections.emptyList();
        List<UsuarioPlataforma> porTexto = Collections.emptyList();

        if (rolId != null) {
            RolSistema rol = rolService.obtenerPorId(rolId);
            if (rol != null) {
                porRol = usuarioService.buscarPorRol(rol);
            }
        }

        if (desde != null && hasta != null) {
            porFechas = usuarioService.buscarPorRangoFechas(desde, hasta);
        }

        if (texto != null && !texto.isBlank()) {
            porTexto = usuarioService.buscarPorTexto(texto);
        }

        model.addAttribute("roles", rolService.listarTodos());
        model.addAttribute("porRol", porRol);
        model.addAttribute("porFechas", porFechas);
        model.addAttribute("porTexto", porTexto);

        return "consultas/index";
    }
}

