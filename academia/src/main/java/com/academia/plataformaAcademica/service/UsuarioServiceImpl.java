package com.academia.plataformaAcademica.service;

import com.academia.plataformaAcademica.domain.RolSistema;
import com.academia.plataformaAcademica.domain.UsuarioPlataforma;
import com.academia.plataformaAcademica.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                              PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioPlataforma> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioPlataforma obtenerPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public UsuarioPlataforma guardar(UsuarioPlataforma usuario) {

        String pwd = usuario.getPassword();

        // Si viene una contraseña NUEVA o EDITADA
        if (pwd != null && !pwd.isBlank()) {

            // Si NO está en formato BCrypt, la encriptamos
            if (!pwd.startsWith("$2a$") &&
                !pwd.startsWith("$2b$") &&
                !pwd.startsWith("$2y$")) {

                usuario.setPassword(passwordEncoder.encode(pwd));
            }
        }

        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioPlataforma buscarPorCorreo(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioPlataforma> buscarPorRol(RolSistema rol) {
        return usuarioRepository.findByRolAsignado(rol);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioPlataforma> buscarPorRangoFechas(LocalDateTime desde, LocalDateTime hasta) {
        return usuarioRepository.findByFechaCreacionBetween(desde, hasta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioPlataforma> buscarPorTexto(String texto) {
        return usuarioRepository
                .findByEmailContainingIgnoreCaseOrNombresContainingIgnoreCase(texto, texto);
    }

    @Override
    @Transactional(readOnly = true)
    public long contarActivos() {
        return usuarioRepository.countByActivoTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public long contarInactivos() {
        return usuarioRepository.countByActivoFalse();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioPlataforma> listarOrdenadosPorFechaDesc() {
        return usuarioRepository.findAllByOrderByFechaCreacionDesc();
    }
}
