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

        String pwd = usuario.getContrasenna();

        if (pwd != null && !pwd.isBlank()) {
            // Si la contraseña NO parece un hash BCrypt, la encriptamos
            if (!pwd.startsWith("$2a$") && !pwd.startsWith("$2b$") && !pwd.startsWith("$2y$")) {
                usuario.setContrasenna(passwordEncoder.encode(pwd));
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
    public UsuarioPlataforma buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreoInstitucional(correo);
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
        // Usamos el método CORRECTO del repositorio
        return usuarioRepository
                .findByCorreoInstitucionalContainingIgnoreCaseOrNombreContainingIgnoreCase(texto, texto);
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
        // Este método no existía → lo creamos en el repositorio
        return usuarioRepository.findAllByOrderByFechaCreacionDesc();
    }
}
