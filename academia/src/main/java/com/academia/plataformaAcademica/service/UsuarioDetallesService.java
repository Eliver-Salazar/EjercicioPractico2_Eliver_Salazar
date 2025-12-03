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

        UsuarioPlataforma usuario = usuarioRepository.findByCorreoInstitucional(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        String rol = usuario.getRolAsignado().getNombreRol(); // ADMIN, PROFESOR, ESTUDIANTE

        return User
                .builder()
                .username(usuario.getCorreoInstitucional())
                .password(usuario.getContrasenna())   // ← YA VIENE HASHED
                .disabled(!usuario.getActivo())
                .roles(rol)   // crea ROLE_ADMIN etc.
                .build();
    }
}
