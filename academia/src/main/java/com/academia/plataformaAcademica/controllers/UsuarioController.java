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
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final RolService rolService;

    public UsuarioController(UsuarioService usuarioService, RolService rolService) {
        this.usuarioService = usuarioService;
        this.rolService = rolService;
    }

    @GetMapping
    public String listar(Model model) {
        List<UsuarioPlataforma> usuarios = usuarioService.listarTodos();
        model.addAttribute("usuarios", usuarios);
        return "usuario/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("usuario", new UsuarioPlataforma());
        model.addAttribute("roles", rolService.listarTodos());
        return "usuario/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("usuario") UsuarioPlataforma usuario,
                          BindingResult result,
                          @RequestParam("rolId") Long rolId,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        RolSistema rol = rolService.obtenerPorId(rolId);
        usuario.setRolAsignado(rol);

        if (result.hasErrors()) {
            model.addAttribute("roles", rolService.listarTodos());
            return "usuario/formulario";
        }

        usuarioService.guardar(usuario);
        redirectAttributes.addFlashAttribute("mensaje", "Usuario guardado correctamente");
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        UsuarioPlataforma usuario = usuarioService.obtenerPorId(id);
        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
            return "redirect:/admin/usuarios";
        }
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolService.listarTodos());
        return "usuario/formulario";
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        UsuarioPlataforma usuario = usuarioService.obtenerPorId(id);
        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
            return "redirect:/admin/usuarios";
        }
        model.addAttribute("usuario", usuario);
        return "usuario/detalle";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        usuarioService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensaje", "Usuario eliminado");
        return "redirect:/admin/usuarios";
    }
}

