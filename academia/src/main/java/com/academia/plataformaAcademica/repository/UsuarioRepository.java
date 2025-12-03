package com.academia.plataformaAcademica.repository;

import com.academia.plataformaAcademica.domain.UsuarioPlataforma;
import com.academia.plataformaAcademica.domain.RolSistema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UsuarioRepository extends JpaRepository<UsuarioPlataforma, Long> {

    // 1) Buscar usuarios por rol
    List<UsuarioPlataforma> findByRolAsignado(RolSistema rol);

    // 2) Buscar usuarios creados en un rango de fechas
    List<UsuarioPlataforma> findByFechaCreacionBetween(LocalDateTime desde, LocalDateTime hasta);

    // 3) Buscar por coincidencia parcial en correo o nombre (CORRECTO)
    List<UsuarioPlataforma> findByCorreoInstitucionalContainingIgnoreCaseOrNombreContainingIgnoreCase(
            String fragmentoCorreo,
            String fragmentoNombre
    );

    // 4) Contar usuarios activos vs inactivos
    long countByActivoTrue();
    long countByActivoFalse();

    // 5) Buscar usuario exacto por correo (para login)
    UsuarioPlataforma findByCorreoInstitucional(String correo);
    
    List<UsuarioPlataforma> findAllByOrderByFechaCreacionDesc();

}
