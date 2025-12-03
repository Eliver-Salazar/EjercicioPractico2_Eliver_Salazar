/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.academia.plataformaAcademica.service;

/**
 *
 * @author Eliver Salazar Campo
 */

import com.academia.plataformaAcademica.domain.RolSistema;
import com.academia.plataformaAcademica.domain.UsuarioPlataforma;

import java.time.LocalDateTime;
import java.util.List;

public interface UsuarioService {

    List<UsuarioPlataforma> listarTodos();

    UsuarioPlataforma obtenerPorId(Long id);

    UsuarioPlataforma guardar(UsuarioPlataforma usuario);

    void eliminar(Long id);

    UsuarioPlataforma buscarPorCorreo(String correo);

    // Consultas avanzadas
    List<UsuarioPlataforma> buscarPorRol(RolSistema rol);

    List<UsuarioPlataforma> buscarPorRangoFechas(LocalDateTime desde, LocalDateTime hasta);

    List<UsuarioPlataforma> buscarPorTexto(String texto);

    long contarActivos();

    long contarInactivos();

    List<UsuarioPlataforma> listarOrdenadosPorFechaDesc();
}

