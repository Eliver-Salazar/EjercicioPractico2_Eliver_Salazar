package com.academia.plataformaAcademica.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    // Redirige a login si entran a la raíz
    @GetMapping("/")
    public String raiz() {
        return "redirect:/login";
    }

    // Página de login (GET)
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    
    @GetMapping("/postLogin")
    public String postLogin(Authentication auth) {

        if (auth == null) {
            return "redirect:/login";
        }

        String rolUsuario = obtenerRol(auth);

        if (rolUsuario == null) {
            return "redirect:/login?errorRol";
        }

        switch (rolUsuario) {
            case "ROLE_ADMIN":
                return "redirect:/admin/usuarios";

            case "ROLE_PROFESOR":
                return "redirect:/profesor/reportes";

            case "ROLE_ESTUDIANTE":
                return "redirect:/estudiante/perfil";

            default:
                return "redirect:/login?errorRol";
        }
    }

    // Obtiene el PRIMER rol del usuario autenticado
    private String obtenerRol(Authentication auth) {
        if (auth.getAuthorities() == null || auth.getAuthorities().isEmpty()) {
            return null;
        }

        GrantedAuthority authority = auth.getAuthorities().iterator().next();
        return authority.getAuthority(); // Ej: ROLE_ADMIN
    }
}
