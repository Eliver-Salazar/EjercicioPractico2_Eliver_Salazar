/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.academia.plataformaAcademica.controllers;

/**
 *
 * @author Eliver Salazar Campo
 */

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/")
    public String raiz() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/postLogin")
    public String postLogin(Authentication auth) {
        if (auth == null) {
            return "redirect:/login";
        }

        boolean esAdmin = tieneRol(auth, "ROLE_ADMIN");
        boolean esProfesor = tieneRol(auth, "ROLE_PROFESOR");
        boolean esEstudiante = tieneRol(auth, "ROLE_ESTUDIANTE");

        if (esAdmin) {
            return "redirect:/admin/usuarios";
        } else if (esProfesor) {
            return "redirect:/profesor/reportes";
        } else if (esEstudiante) {
            return "redirect:/estudiante/perfil";
        }

        return "redirect:/login";
    }

    private boolean tieneRol(Authentication auth, String rolBuscado) {
        for (GrantedAuthority authority : auth.getAuthorities()) {
            if (rolBuscado.equals(authority.getAuthority())) {
                return true;
            }
        }
        return false;
    }
}

