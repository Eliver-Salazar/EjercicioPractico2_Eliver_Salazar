package com.academia.plataformaAcademica.repository;

import com.academia.plataformaAcademica.domain.UsuarioPlataforma;
import com.academia.plataformaAcademica.domain.RolSistema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<UsuarioPlataforma, Long> {

    // Buscar usuarios por rol
    List<UsuarioPlataforma> findByRolAsignado(RolSistema rol);

    // Buscar usuarios por fechas
    List<UsuarioPlataforma> findByFechaCreacionBetween(LocalDateTime desde, LocalDateTime hasta);

    // Busqueda parcial por email o nombres
    List<UsuarioPlataforma> findByEmailContainingIgnoreCaseOrNombresContainingIgnoreCase(
            String fragmentoEmail,
            String fragmentoNombre
    );

    // Contadores
    long countByActivoTrue();
    long countByActivoFalse();

    // Buscar usuario EXACTO por email (LOGIN)
    UsuarioPlataforma findByEmail(String email);

    // Ordenados por fecha
    List<UsuarioPlataforma> findAllByOrderByFechaCreacionDesc();
}
