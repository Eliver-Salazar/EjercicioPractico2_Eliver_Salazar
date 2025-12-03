package com.academia.plataformaAcademica.service;

import com.academia.plataformaAcademica.domain.UsuarioPlataforma;
import com.academia.plataformaAcademica.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetallesService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetallesService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Aquí "username" es el email que el usuario digita en el login
        UsuarioPlataforma usuario = usuarioRepository.findByEmail(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        // Ej: ADMIN, PROFESOR, ESTUDIANTE (sin el prefijo ROLE_)
        String rol = usuario.getRolAsignado().getNombreRol();

        return User
                .builder()
                // username será el email del usuario
                .username(usuario.getEmail())
                // password YA debe venir encriptado con BCrypt desde UsuarioServiceImpl
                .password(usuario.getPassword())
                // Si activo = false, entonces la cuenta queda deshabilitada
                .disabled(!Boolean.TRUE.equals(usuario.getActivo()))
                // Spring le agrega automáticamente el prefijo ROLE_ → ROLE_ADMIN, etc.
                .roles(rol)
                .build();
    }
}
